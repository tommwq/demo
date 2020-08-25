package main

import (
	"fmt"
)

type Employee struct {
	Name    string
	Sallary float64
}

type Visitor interface {
	Visit(*Employee)
}

type Company struct {
	employees []*Employee
}

func NewCompany() *Company {
	return &Company{
		employees: make([]*Employee, 0),
	}
}

func (r *Company) Employ(e *Employee) {
	r.employees = append(r.employees, e)
}

func (r *Company) Accept(v Visitor) {
	for _, e := range r.employees {
		v.Visit(e)
	}
}

type SallaryPrinter struct{}
type SallaryUpdater struct {
	Scale float64
}

func (r *SallaryPrinter) Visit(e *Employee) {
	fmt.Printf("%v %.2f\n", e.Name, e.Sallary)
}

func (r *SallaryUpdater) Visit(e *Employee) {
	e.Sallary *= r.Scale
}

func main() {

	company := NewCompany()
	company.Employ(&Employee{Name: "Hank", Sallary: 100.0})
	company.Employ(&Employee{Name: "Elly", Sallary: 120.0})
	company.Employ(&Employee{Name: "Frank", Sallary: 90.0})

	company.Accept(&SallaryPrinter{})
	company.Accept(&SallaryUpdater{Scale: 1.10})
	company.Accept(&SallaryPrinter{})
}
