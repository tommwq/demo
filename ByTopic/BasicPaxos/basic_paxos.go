package main

import (
	"bytes"
	"encoding/gob"
	"fmt"
	"log"
	"time"
)

type Message struct {
	Type   string
	From   int
	To     int
	Number int
	Value  string
}

func (r Message) String() string {
	return fmt.Sprintf("#%d -> #%d %v %v %v", r.From, r.To, r.Type, r.Number, r.Value)
}

type Acceptor struct {
	node           *Node
	PromisedNumber int
	AcceptedNumber int
	AcceptedValue  string
}

func (r *Acceptor) OnMessage(message Message) {
	switch message.Type {
	case "prepare":
		r.prepare(message)
	case "accept":
		r.accept(message)
	}
}

func (r *Acceptor) prepare(message Message) {
	if message.Number < r.PromisedNumber {
		return
	}

	r.PromisedNumber = message.Number
	r.node.Manager().SendToID(message.From, Message{
		Type:   "prepare-response",
		From:   r.node.ID(),
		To:     message.From,
		Number: r.AcceptedNumber,
		Value:  r.AcceptedValue,
	})
}

func (r *Acceptor) accept(message Message) {
	if message.Number < r.PromisedNumber {
		return
	}

	r.AcceptedNumber = message.Number
	r.AcceptedValue = message.Value
	log.Printf("#%d accepted %v\n", r.node.ID(), message.Value)
}

func (r *Acceptor) Bind(node *Node) {
	r.node = node
}

type Proposer struct {
	node      *Node
	Number    int
	Value     string
	maxNumber int
	maxValue  string
	count     int
	over      bool
}

func (r *Proposer) OnMessage(message Message) {
	if r.over {
		return
	}

	switch message.Type {
	case "prepare-response":
		r.onPrepareResponse(message)
	case "timeout":
		r.onTimeOut(message)
	}
}

func (r *Proposer) onTimeOut(message Message) {
	log.Printf("TIMEOUT %v\n", r.node.ID())
	r.over = true
}

func (r *Proposer) onPrepareResponse(message Message) {
	if message.Number > r.maxNumber {
		r.maxNumber = message.Number
		r.maxValue = message.Value
	}

	r.count = r.count + 1

	if r.count >= 2 {
		r.over = true

		if r.maxNumber != 0 {
			r.Value = r.maxValue
		}

		r.node.Manager().SendToRole("a", Message{
			Type:   "accept",
			From:   r.node.ID(),
			Number: r.Number,
			Value:  r.Value,
		})
	}
}

func (r *Proposer) Bind(node *Node) {
	r.node = node
}

func (r *Proposer) Propose(message string) {
	r.Value = message
	r.node.Manager().SendToRole("a", Message{
		Type:   "prepare",
		From:   r.node.ID(),
		To:     0,
		Number: r.Number,
	})
	r.node.Manager().SendToIDDelay(r.node.ID(), Message{Type: "timeout"}, 2000)
}

type MessageHandler interface {
	Bind(*Node)
	OnMessage(Message)
}

type Node struct {
	mailbox     chan Message
	id          int
	role        string
	manager     *NodeManager
	handler     MessageHandler
	signalQueue chan struct{}
	running     bool
}

func (r Node) ID() int {
	return r.id
}

func (r *Node) run() {
	for r.running {
		select {
		case <-r.signalQueue:
			r.running = false
		case message := <-r.mailbox:
			r.handler.OnMessage(message)
		}
	}
}

func (r Node) SignalQueue() chan<- struct{} {
	return r.signalQueue
}

func (r *Node) Wait() {
	for r.running {
		time.Sleep(time.Second)
	}
}

func (r *Node) Start() {
	r.running = true
	go r.run()
}

func (r *Node) Stop() {
	r.SignalQueue() <- struct{}{}
}

func (r Node) Role() string {
	return r.role
}

func (r Node) Manager() *NodeManager {
	return r.manager
}

func (r Node) Input() chan<- Message {
	return r.mailbox
}

type NodeManager struct {
	nodes  map[int]*Node
	nextID int
}

func NewNodeManager() *NodeManager {
	return &NodeManager{
		nodes:  make(map[int]*Node),
		nextID: 1,
	}
}

func (r NodeManager) SendToRole(role string, message Message) {
	for _, node := range r.nodes {
		if node.Role() == role {
			message.To = node.ID()
			node.Input() <- message
			log.Println(message)
		}
	}
}

func (r NodeManager) SendToID(id int, message Message) {
	node, ok := r.nodes[id]
	if !ok {
		return
	}

	node.Input() <- message
	log.Println(message)
}

func (r NodeManager) SendToIDDelay(id int, message Message, millisecond int) {
	go func(m NodeManager) {
		time.Sleep(time.Duration(millisecond) * time.Millisecond)
		node, ok := m.nodes[id]
		if !ok {
			return
		}

		node.Input() <- message
		log.Println(message)
	}(r)
}

func (r *NodeManager) NewNode(role string, handler MessageHandler) *Node {
	node := &Node{
		mailbox:     make(chan Message, 1024),
		id:          r.nextID,
		role:        role,
		manager:     r,
		handler:     handler,
		signalQueue: make(chan struct{}),
	}
	handler.Bind(node)
	r.nodes[r.nextID] = node

	r.nextID = r.nextID + 1
	return node
}

func main() {
	manager := NewNodeManager()
	n1 := manager.NewNode("a", &Acceptor{})
	n1.Start()
	manager.NewNode("a", &Acceptor{}).Start()
	manager.NewNode("a", &Acceptor{}).Start()

	p1 := &Proposer{
		Number: 1,
	}
	manager.NewNode("p", p1).Start()

	p2 := &Proposer{
		Number: 2,
	}
	manager.NewNode("p", p2).Start()

	go p1.Propose("hello")
	go p2.Propose("world")
	n1.Wait()
}

func (r Message) Encode() ([]byte, error) {
	var buffer bytes.Buffer
	encoder := gob.NewEncoder(&buffer)
	err := encoder.Encode(r)
	return buffer.Bytes(), err
}

func (r *Message) Decode(data []byte) error {
	buffer := bytes.NewBuffer(data)
	decoder := gob.NewDecoder(buffer)
	return decoder.Decode(r)
}
