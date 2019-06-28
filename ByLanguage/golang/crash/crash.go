package main

import "fmt"

func main() {
	var x int
	fmt.Println(foo(x))
}

func foo(x int) int {
	return 100 / x
}
