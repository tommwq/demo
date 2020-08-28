package main

import "fmt"

type Adapter1 struct {
	origin *Origin
}

func NewAdapter1(origin *Origin) *Adapter1 {
	return &Adapter1{origin: origin}
}

func (r *Adapter1) SayHello() {
	fmt.Println(r.origin.SayHello())
}

type Adapter2 struct {
	Origin
}

func NewAdapter2(origin *Origin) *Adapter2 {
	return &Adapter2{Origin: *origin}
}

func (r *Adapter2) SayHello() {
	fmt.Println(r.Origin.SayHello())
}
