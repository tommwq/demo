package main

import (
	"log"
	"container/list"
)

type Handler interface {
	Handle(value int) int
}

type HandlerChain struct {
	handlers *list.List
}

func NewHandlerChain() *HandlerChain {
	return &HandlerChain{
		handlers: list.New(),
	}
}

func (r *HandlerChain) AddHandler(handler Handler) {
	r.handlers.PushBack(handler)
}

func (r *HandlerChain) Handle(value int) int {
	for e := r.handlers.Front(); e != nil; e = e.Next() {
		value = e.Value.(Handler).Handle(value)
	}

	return value
}

type Doubler struct {}
func (r *Doubler) Handle(value int) int {
	return value * 2
}

type Increaser struct {}
func (r *Increaser) Handle(value int) int {
	return value + 1
}

func main() {
	
	doubler := &Doubler{}
	increaser := &Increaser{}

	chain := NewHandlerChain()
	chain.AddHandler(doubler)
	chain.AddHandler(doubler)
	chain.AddHandler(increaser)
	
	x := chain.Handle(1)
	log.Println(x)
}
