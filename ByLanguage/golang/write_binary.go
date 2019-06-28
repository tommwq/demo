package main

import (
	"encoding/binary"
	"log"
)

func main() {
	b := make([]byte, 12)
	x := uint32(1)
	y := uint32(1)

	binary.LittleEndian.PutUint32(b[4:], x)
	binary.LittleEndian.PutUint32(b[8:], y)

	log.Println(b)
}
