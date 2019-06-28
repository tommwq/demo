package main

import (
	"log"
	"unsafe"
)

type Slice struct {
	Data uintptr
	Len int
	Cap int
}

func main() {
	//i := 0
	a := [...]int{1,2,3}
	var s []int = a[:]

	log.Printf("%p %p\n", &a, s)

	p := unsafe.Pointer(&s)
	x := (*Slice)(p)
	log.Printf("%v\n", x)


	p = unsafe.Pointer(x.Data)
	pi := (*int)(p)
	*pi = 2
	
	for _, k := range s {
		log.Println(k)
	}

	s = append(s, 4)
	p = unsafe.Pointer(&s)
	x = (*Slice)(p)
	log.Printf("%v\n", x)
}


