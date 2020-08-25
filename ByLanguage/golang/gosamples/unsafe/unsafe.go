package main

import (
	"fmt"
	"unsafe"
)

func main() {
	cast()
	accessArray()
	accessStruct()
}

func cast() {
	var f float64 = 0.0
	var x uint64 = *(*uint64)(unsafe.Pointer(&f))
	fmt.Println(f, x)
}

func accessArray() {
	array := []int{1, 2, 3}
	base := uintptr(unsafe.Pointer(&array[0]))
	size := unsafe.Sizeof(array[0])
	ptr := unsafe.Pointer(base + 2*size)
	element := *(*int)(ptr)

	fmt.Println(element, array[2])
}

type Foo struct {
	A int
	B int
}

func accessStruct() {
	foo := &Foo{1, 2}
	fmt.Println(foo)

	base := uintptr(unsafe.Pointer(foo))
	offset := unsafe.Offsetof(foo.A)

	ptr := unsafe.Pointer(base + offset)
	*(*int)(ptr) = 3

	fmt.Println(foo)
}

func sum(arr []int) int {
	sum := 0
	for _, x := range arr {
		sum += x
	}
	return sum
}

func unsafeSum(arr []int) int {
	ptr := unsafe.Pointer(&arr[0])
	count := len(arr)
	step := unsafe.Sizeof(arr[0])

	sum := 0
	for i := 0; i < count; i++ {
		sum += *(*int)(ptr)
		ptr = unsafe.Pointer(uintptr(ptr) + step)
	}

	return sum
}

func unsafeSum2(arr []int) int {
	base := unsafe.Pointer(&arr[0])
	count := len(arr)
	step := unsafe.Sizeof(arr[0])

	sum := 0
	for i := 0; i < count; i++ {
		ptr := unsafe.Pointer(uintptr(base) + uintptr(i)*step)
		sum += *(*int)(ptr)
	}

	return sum
}

func unsafeSum3(arr []int) int {
	beg := unsafe.Pointer(&arr[0])
	size := unsafe.Sizeof(arr[0])
	end := unsafe.Pointer(uintptr(beg) + uintptr(len(arr))*size)

	ptr := beg
	sum := 0

	for ptr != end {
		sum += *(*int)(ptr)
		ptr = unsafe.Pointer(uintptr(ptr) + size)
	}

	return sum
}
