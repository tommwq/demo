package main

type ProductA interface {
	String() string
}

type ProductB interface {
	String() string
}

type AbstractFactory interface {
	CreateProductA() ProductA
	CreateProductB() ProductB
}
