package main

type Processor interface {
	Process(*Payload) *Payload
	Initialize(interface{})
	CleanUp()
}
