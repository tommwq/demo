package main

import (
	"log"
	"errors"
	"time"
)

type Node struct {
	mailbox     chan Message
	id          string
	role        string
	manager     *NodeManager
	handler     MessageHandler
	signalQueue chan struct{}
	running     bool
}

func (r Node) ID() string {
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
	nodes  map[string]*Node
	nextID int
}

func NewNodeManager() *NodeManager {
	return &NodeManager{
		nodes:  make(map[string]*Node),
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

func (r NodeManager) SendToID(id string, message Message) {
	node, ok := r.nodes[id]
	if !ok {
		return
	}

	node.Input() <- message
	log.Println(message)
}

func (r NodeManager) SendToIDDelay(id string, message Message, millisecond int) {
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

func (r *NodeManager) NewNode(id, role, handler string) (*Node, error) {
	h, ok := getMessageHandler(handler)
	if !ok {
		return nil, errors.New("handler not found")
	}
	
	node := &Node{
		mailbox:     make(chan Message, 1024),
		id:          id,
		role:        role,
		manager:     r,
		handler:     h,
		signalQueue: make(chan struct{}),
	}
	h.Bind(node)
	r.nodes[id] = node

	r.nextID = r.nextID + 1
	return node, nil
}

var (
	handlers = make(map[string]MessageHandler)
)

func getMessageHandler(name string) (MessageHandler, bool) {
	h, ok := handlers[name]
	return h, ok
}

func registerMessageHandler(name string, handler MessageHandler) bool {
	_, exist := handlers[name]
	if exist {
		return false
	}
	
	handlers[name] = handler
	return true
}

