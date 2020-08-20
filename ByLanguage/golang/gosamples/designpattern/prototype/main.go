package main

type Prototype interface {
	Clone() Prototype
}

type ConcretePrototype1 struct {
	Name string
}

func (r *ConcretePrototype1) Clone() Prototype {
	return &ConcretePrototype1{
		Name: r.Name,
	}
}

type ConcretePrototype2 struct {
	Address string
}

func (r *ConcretePrototype2) Clone() Prototype {
	return &ConcretePrototype2{
		Address: r.Address,
	}
}

func main() {
	c1 := &ConcretePrototype1{"Frank"}
	c2 := &ConcretePrototype2{"Berk S.T."}

	c3 := c1.Clone().(*ConcretePrototype1)
	println(c3.Name)
}
