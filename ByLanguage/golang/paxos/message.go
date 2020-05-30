package main

import (
	"fmt"
	"log"
	"sync"
)

type Message struct {
	Category MessageCategory
	Target   int
	Source   int
	Number   int
	Value    string
}

func (r *Message) Clone() *Message {

	return &Message{
		Category: r.Category,
		Target:   r.Target,
		Source:   r.Source,
		Number:   r.Number,
		Value:    r.Value,
	}
}

func (r *Message) String() string {
	return fmt.Sprintf("Message<%v,%v,%v,%v,%v>",
		r.Category,
		r.Target,
		r.Source,
		r.Number,
		r.Value)
}

type MessageBus struct {
	nodes map[int]*Node
	roles map[Role][]*Node
	lock  sync.Mutex
}

func NewMessageBus() *MessageBus {
	m := &MessageBus{
		nodes: make(map[int]*Node),
		roles: make(map[Role][]*Node),
	}
	m.roles[RoleAcceptor] = make([]*Node, 0)
	m.roles[RoleProposer] = make([]*Node, 0)
	m.roles[RoleLearner] = make([]*Node, 0)

	return m
}

func (r *MessageBus) AddNode(node *Node) {
	r.lock.Lock()
	id := node.ID()
	r.nodes[id] = node
	if node.IsAcceptor() {
		r.roles[RoleAcceptor] = append(r.roles[RoleAcceptor], node)
	}
	if node.IsProposer() {
		r.roles[RoleProposer] = append(r.roles[RoleProposer], node)
	}
	if node.IsLearner() {
		r.roles[RoleLearner] = append(r.roles[RoleLearner], node)
	}
	r.lock.Unlock()
}

func (r *MessageBus) RemoveNode(nodeID int) {
}

func (r *MessageBus) SendToPeer(peer int, message *Message, node *Node) {
	r.lock.Lock()
	defer r.lock.Unlock()

	target, ok := r.nodes[peer]
	if !ok {
		log.Printf("MISSING TARGET, DISCARD %v\n", message)
		return
	}
	message.Source = node.ID()
	message.Target = peer
	log.Printf("%v SEND %v TO %v\n", node, message, target)
	target.Receive(message)
}

func (r *MessageBus) SendToRole(role Role, message *Message, node *Node) {
	log.Printf("%v SEND %v TO %v\n", node, message, role)
	message.Source = node.ID()
	r.lock.Lock()
	for _, a := range r.roles[role] {
		m := message.Clone()
		m.Target = a.ID()
		log.Printf("%v SEND %v TO %v\n", node, m, a)
		a.Receive(m)
	}
	r.lock.Unlock()
}
