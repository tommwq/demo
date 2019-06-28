package main

import (
	"log"
	"math/rand"
	"time"
)

func init() {
	rand.Seed(time.Now().UnixNano())
}

type Acceptor struct {
	accepts []*Message
	node    *Node
	promise int
}

func (r *Acceptor) Process(m *Message) {
	switch m.Category {
	case Prepare:
		r.onPrepare(m)
	case Accept:
		r.onAccept(m)
	}
}

func (r *Acceptor) onPrepare(m *Message) {
	if m.Number < r.promise {
		log.Printf("%v DISCARD %v\n", r.node, m)
		return
	}

	r.promise = m.Number
	an := -1
	av := ""

	for _, ac := range r.accepts {
		if ac.Number > an {
			an = ac.Number
			av = ac.Value
		}
	}

	time.Sleep(time.Duration(rand.Intn(1000)) * time.Millisecond)

	r.node.SendToPeer(m.Source, &Message{
		Category: Promise,
		Target:   m.Source,
		Source:   r.node.ID(),
		Number:   an,
		Value:    av,
	})
}

func (r *Acceptor) onAccept(m *Message) {
	log.Println("ACCEPT", m)
}

func NewAcceptor(mb *MessageBus) *Acceptor {
	a := &Acceptor{
		accepts: make([]*Message, 0),
		promise: -1,
	}

	n := NewNode()
	n.AddRole(RoleAcceptor)
	n.SetMessageBus(mb)
	n.SetLogicProcessor(a)
	n.Start()
	a.node = n
	return a
}
