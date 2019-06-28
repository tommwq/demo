package main

import (
	"os"
)

func main() {
	for i, arg := range os.Args {
		println(i, arg)
	}
}
