package main

import (
	"log"
	"os"
	"time"
)

func init() {
	log.SetOutput(os.Stdout)
}

func main() {
	messageBus := NewMessageBus()

	p1 := NewProposer(messageBus, 2, 3)
	p2 := NewProposer(messageBus, 3, 3)

	NewAcceptor(messageBus)
	NewAcceptor(messageBus)
	NewAcceptor(messageBus)
	NewAcceptor(messageBus)
	NewAcceptor(messageBus)

	go Post(p1, "hello")
	go Post(p2, "bye")

	time.Sleep(time.Second)
}

func Post(p *Proposer, value string) {
	p.Post(value)
}
