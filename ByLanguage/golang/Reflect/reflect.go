package main

import (
	"fmt"
	"reflect"
)

func main() {
	x := new(X)
	x.Call("Foo")
	x.Call("Fooo")
}

type X struct {}

func (r *X) Foo() {
	fmt.Println("FOO")
}

func (r *X) Call(fname string) bool {
	method := reflect.ValueOf(r).MethodByName(fname)
	if !method.IsValid() {
		return false
	}

	result := method.Call([]reflect.Value{})
	fmt.Println(result)
	
	return true
}
