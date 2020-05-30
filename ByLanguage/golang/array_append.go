package main

import (
	"log"
)

func main() {
	a := []byte("ok")
	b := []byte("OK")
	a = append(a, b...)
	b[0] = byte('o')

	log.Println(a, b)
}
