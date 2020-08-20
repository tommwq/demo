package main

type A struct {
	X, Y int
	ok   func()
}

type B struct {
	*A
}

func NewA() *A {
	a := new(A)
	a.ok = func() { println("ok") }
	return a
}

func NewB() *B {
	a := new(A)
	b := new(B)
	b.A = *a
	return b
}
func x(a *A) {
	println(a.X, a.Y)
}

func y(a *A) {
	a.X = 1
}

func main() {

	b := NewB()

	y(&b.A)
	x(&b.A)
	b.A.ok()

}
