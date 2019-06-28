package main

import "fmt"

type Base struct {x int}

type Derived struct {
	Base
}

func (b *Base) incr(x int) int {
	b.x += x
	return b.x
}

func (d *Derived) incr(x int) int {
	d.Base.x += x + 1
	return d.Base.x
}

func main() {
	a := Derived{Base{10}}
	fmt.Println(a.Base.x)
	fmt.Println(a.Base.incr(10))
	fmt.Println(a.incr(10))
}
