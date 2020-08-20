package main

import (
	"fmt"
	"log"
	"time"
)

type Proposer struct {
	node         *Node
	proposal     int
	step         int
	number       int
	value        string
	half         int
	proposalTime time.Time
	timeout      time.Duration
	done         chan bool
	count        int
}

func (r *Proposer) Process(m *Message) {
	switch m.Category {
	case Promise:
		r.onPromise(m)
	case Accept:
		r.onAccept(m)
	}
}

func (r *Proposer) onAccept(m *Message) {
}

func (r *Proposer) onPromise(m *Message) {
	r.count++

	if r.number < m.Number {
		r.number = m.Number
		r.value = m.Value
	}

	if r.value == "" {
		r.value = fmt.Sprintf("%v", r.node.ID())
	}

	if r.count > r.half {
		r.count = 0
		r.node.SendToRole(RoleAcceptor, &Message{Accept, 0, r.node.ID(), r.proposal, r.value})
	}
}

func (r *Proposer) Post(value string) {
	r.proposal += r.step
	r.node.SendToRole(RoleAcceptor, &Message{
		Category: Prepare,
		Target: 0,
		Source: 0,
		Number: r.proposal,
	})
	// TODO set timeout check
}

func NewProposer(messageBus *MessageBus, step, half int) *Proposer {
	p := &Proposer{
		proposal: step,
		step:     step,
		half:     half,
		timeout:  1000 * time.Millisecond,
	}
	n := NewNode()
	n.AddRole(RoleProposer)
	n.SetMessageBus(messageBus)
	n.SetLogicProcessor(p)
	p.node = n
	n.Start()
	return p
}
