package main

import (
	"sync"
	"fmt"
)

type Foo struct {
	x int
}

func makeFoo() interface{} {
	return &Foo{x:1}
}

func main() {
	p := sync.Pool{}
	p.New = makeFoo

	f1 := p.Get().(*Foo)
	f2 := p.Get().(*Foo)
	f1.x = 1
	f2.x = 2
	fmt.Println(f1)
	fmt.Println(f2)
	p.Put(f1)
	f3 := p.Get()
	fmt.Println(f3)
}
