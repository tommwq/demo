package main

import (
	"fmt"
)

func main() {
	data := []byte("abc")

	fmt.Println(string(data[:2]))
	fmt.Println(string(data[:3]))
	fmt.Println(string(data[:]))
}
