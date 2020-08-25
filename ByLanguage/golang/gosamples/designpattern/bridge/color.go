package main

const (
	Red  = "red"
	Blue = "blue"
)

type Color struct {
	color string
}

func NewColor(color string) *Color {
	return &Color{color: color}
}

func (r *Color) String() string {
	return r.color
}
