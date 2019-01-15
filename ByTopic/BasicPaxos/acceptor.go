package main

import (
	"log"
)

// PrepareRequestArrived

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
