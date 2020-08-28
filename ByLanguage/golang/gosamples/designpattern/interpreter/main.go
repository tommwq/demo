package main

type Customer struct {
	Name string
}

type Servable interface {
	Receive(*Customer)
}

type Doorman struct{}

func (r *Doorman) Receive(c *Customer) {
	println("Hello,", c.Name)
}

type Waiter struct{}

func (r *Waiter) Receive(c *Customer) {
	println("Can I help your?")
}

type Bar struct {
	waiter  *Waiter
	doorman *Doorman
}

func NewBar() *Bar {
	return &Bar{
		waiter:  &Waiter{},
		doorman: &Doorman{},
	}
}
func (r *Bar) Receive(c *Customer) {
	r.doorman.Receive(c)
	r.waiter.Receive(c)
}

func main() {
	jeff := &Customer{Name: "Jeff"}
	bar := NewBar()
	bar.Receive(jeff)
}
