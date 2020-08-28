package main

import (
	"log"
)

type Printer struct{}

func (r *Printer) Process(p *Payload) *Payload {
	log.Println(p.Payload)
	return nil
}

func (r *Printer) Initialize(param interface{}) {
	log.Println("initialize", param)
}

func (r *Printer) CleanUp() {
}
