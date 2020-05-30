package main

import "fmt"

func main() {
	foo()
	bar()
	xyz()
}

func bar() {
	defer fmt.Println("world")

	fmt.Println("hello")
}


func foo() {
	fmt.Println("counting")
	for i := 0; i < 10; i++ {
		defer fmt.Println(i)
	}
	fmt.Println("done")
}


func xyz() {
	defer abc(1)
	defer abc(2)

	abc(3)
}

func abc(x int) {
	log.Println(x)
}
