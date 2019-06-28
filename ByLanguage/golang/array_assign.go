package main

import (
	"log"
)

func main() {
	a := []byte("1234")
	b := a
	log.Println(string(a), string(b))

	b[0] = byte('0')
	log.Println(string(a), string(b))

	b = append(a, []byte("56")...)
	b[0] = byte('1')
	log.Println(string(a), string(b))

	c := make([]byte, len(a))
	copy(c, a)
	b = append(c, []byte("56")...)
	b[0] = byte('0')
	log.Println(string(a), string(b))
}
