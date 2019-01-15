package main

import (
	"bytes"
	"encoding/gob"
	"fmt"
)

type Message struct {
	Type   string
	From   string
	To     string
	Number int
	Value  string
}

func (r Message) String() string {
	return fmt.Sprintf("#%d -> #%d %v %v %v", r.From, r.To, r.Type, r.Number, r.Value)
}

func (r Message) Encode() ([]byte, error) {
	var buffer bytes.Buffer
	encoder := gob.NewEncoder(&buffer)
	err := encoder.Encode(r)
	return buffer.Bytes(), err
}

func (r *Message) Decode(data []byte) error {
	buffer := bytes.NewBuffer(data)
	decoder := gob.NewDecoder(buffer)
	return decoder.Decode(r)
}

type MessageHandler interface {
	Bind(*Node)
	OnMessage(Message)
}
