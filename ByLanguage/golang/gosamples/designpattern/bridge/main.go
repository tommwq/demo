package main

func main() {
	color := NewColor(Red)
	pen := NewPen(color)
	pen.Paint()
	pen.ChangeColor(NewColor(Blue))
	pen.Paint()
}
