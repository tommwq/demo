package main

import "fmt"

type Pen struct {
	color *Color
}

func NewPen(color *Color) *Pen {
	return &Pen{color: color}
}

func (r *Pen) Paint() {
	fmt.Printf("PAINT WITH COLOR [%v]\n", r.color)
}

func (r *Pen) ChangeColor(color *Color) {
	r.color = color
}
