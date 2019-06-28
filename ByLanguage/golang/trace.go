// go tool trace a.exe a.trace
package main

import (
	"log"
	"os"
	"runtime/trace"
)

func main() {
	filename := "a.trace"
	file, err := os.Create(filename)
	if err != nil {
		log.Fatalln(err)
	}
	defer file.Close()

	trace.Start(file)
	foo()
	trace.Stop()
}

func foo() {
	for i := 0; i < 30; i++ {
		log.Println(fact(i))
	}
}

func fact(x int) int {

	if x <= 1 {
		return 1
	}
	return x * fact(x-1)
}
