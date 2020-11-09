package main

import (
	// "os"
	// "encoding/json"
	// "io/ioutil"
	"fmt"
)

func main() {
	binaryPack := NewBinaryPack(4)
	binaryPack.AddPart(Part{
		Field:         "a",
		OffsetOfField: 0,
		BitNumber:     4,
		OffsetOfPack:  0,
	})

	bits := binaryPack.Pack(map[string]uint64{
		"a": 15,
	})

	fmt.Printf("%v", bits)
}

type Part struct {
	Field         string
	OffsetOfField int
	BitNumber     int
	OffsetOfPack  int
}

type BinaryPack struct {
	byteNumber int
	parts      []Part
}

func NewBinaryPack(byteNumber int) *BinaryPack {
	return &BinaryPack{
		byteNumber: byteNumber,
		parts:      make([]Part, 0, 16),
	}
}

func (r *BinaryPack) AddPart(part Part) {
	r.parts = append(r.parts, part)
}

func (r BinaryPack) setPart(buffer []byte, part Part, value uint64) {
	index := part.OffsetOfPack / 8
	offset := part.OffsetOfPack % 8

	partValue := (value >> part.OffsetOfField) &^ (0xffffffffffffffff << part.BitNumber)
	buffer[index] = byte(uint64(buffer[index]) | (partValue >> offset))
}

func (r BinaryPack) Pack(values map[string]uint64) []byte {
	buffer := make([]byte, r.byteNumber)
	for _, part := range r.parts {
		if value, ok := values[part.Field]; ok {
			r.setPart(buffer, part, value)
		}
	}

	return buffer
}

func (r BinaryPack) Unpack(data []byte) map[string]uint64 {
	// TODO
	return make(map[string]uint64)
}
