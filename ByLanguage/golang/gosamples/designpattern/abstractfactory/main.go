package main

import (
	"flag"
	"log"
)

var (
	factories = map[string]AbstractFactory{
		"x": &FactoryX{},
		"y": &FactoryY{},
	}
)

func main() {
	var factory string
	flag.StringVar(&factory, "factory", "x", "factory name, x or y")
	flag.Parse()

	fact, ok := factories[factory]
	if !ok {
		log.Printf("factory [%v] not found.\n", factory)
		return
	}

	pa := fact.CreateProductA()
	pb := fact.CreateProductB()

	log.Println(pa, pb)
}
