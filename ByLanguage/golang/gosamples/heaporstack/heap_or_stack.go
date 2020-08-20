package main

import "fmt"

func main() {
	x, y := F()
	fmt.Printf("&x = %p &y = %p\n", x, y)
}

func G(x int) {
	println(x)
}

func F() (*int, *int) {
	a := 1
	b := a
	c := 3
	x := new(int)
	y := &b
	fmt.Printf("&a = %p &b = %p &c = %p\n", &a, &b, &c)
	G(c)
	return x, y
}
