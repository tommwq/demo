package main

import (
	"fmt"
)

var (
	idGenerator = &IDGenerator{}
)

type LogicProcessor interface {
	Process(*Message)
}

type Node struct {
	id         int
	role       Role
	messageBus *MessageBus
	messages   chan *Message
	processor  LogicProcessor
}

func (r *Node) String() string {
	return fmt.Sprintf("Node<%v,%v>", r.id, r.role)
}

func (r *Node) AddRole(role Role) {
	r.role.Add(role)
}

func (r *Node) SetMessageBus(mb *MessageBus) {
	r.messageBus = mb
	mb.AddNode(r)
}

func NewNode() *Node {
	n := &Node{
		id:       idGenerator.generateID(),
		messages: make(chan *Message, 1024),
	}

	return n
}

func (r *Node) SetLogicProcessor(processor LogicProcessor) {
	r.processor = processor
}

func (r *Node) Start() {
	go func() {
		for {
			m := r.ReadNextMessage()
			if m == nil {
				break
			}
			if r.processor != nil {
				r.processor.Process(m)
			}
		}
	}()
}

func (r *Node) ID() int {
	return r.id
}

func (r *Node) IsAcceptor() bool {
	return r.role.IsAcceptor()
}

func (r *Node) IsProposer() bool {
	return r.role.IsProposer()
}

func (r *Node) IsLearner() bool {
	return r.role.IsLearner()
}

func (r *Node) SendToPeer(peer int, message *Message) {
	r.messageBus.SendToPeer(peer, message, r)
}

func (r *Node) SendToRole(role Role, message *Message) {
	r.messageBus.SendToRole(role, message, r)
}

func (r *Node) ReadNextMessage() *Message {
	return <-r.messages
}

func (r *Node) Receive(message *Message) {
	r.messages <- message
}
