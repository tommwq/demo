package main

import (
	"fmt"
)

type Component interface {
	Add(Component)
	Remove(Component)
	GetChildren() []Component
	Print()
}

type Leaf struct {
	Value int
}

func (r *Leaf) Add(Component)    {}
func (r *Leaf) Remove(Component) {}
func (r *Leaf) GetChildren() []Component {
	return nil
}
func (r *Leaf) Print() {
	fmt.Println(r.Value)
}

type Composite struct {
	Value    int
	Children []Component
}

func (r *Composite) Add(c Component) {
	r.Children = append(r.Children, c)
}
func (r *Composite) Remove(c Component) {
	var i int
	var e Component
	for i, e = range r.Children {
		if e == c {
			break
		}
	}
	if i == len(r.Children) {
		return
	}

	r.Children = append(r.Children[:i], r.Children[i:]...)
}

func (r *Composite) GetChildren() []Component {
	return r.Children
}

func (r *Composite) Print() {
	for _, c := range r.Children {
		c.Print()
	}
	fmt.Println(r.Value)
}

func NewComposite(value int) *Composite {
	return &Composite{
		Value:    value,
		Children: make([]Component, 0),
	}
}

func main() {
	c1 := NewComposite(1)
	l1 := &Leaf{10}
	l2 := &Leaf{20}
	c1.Add(l1)
	c1.Add(l2)

	c2 := NewComposite(2)
	l3 := &Leaf{30}
	c2.Add(l3)
	c2.Add(c1)

	c2.Print()

	// should print: 30 10 20 1 2
}
