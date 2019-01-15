package main

import (
	"log"
)

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
		To:     "",
		Number: r.Number,
	})
	r.node.Manager().SendToIDDelay(r.node.ID(), Message{Type: "timeout"}, 2000)
}
