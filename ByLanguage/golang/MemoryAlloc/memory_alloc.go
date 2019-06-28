package main

import (
	"fmt"
	"unsafe"
)

var gx, gy int

func main() {
	var lx, ly int
	fmt.Println("本地变量")
	fmt.Printf("%p(%d)\n%p(%d)\n", &lx, &lx, &ly, &ly)

	fmt.Println("全局变量")
	fmt.Printf("%p(%d)\n%p(%d)\n", &gx, &gx, &gy, &gy)

	nx := new(int)
	ny := new(int)
	fmt.Println("new变量")
	fmt.Printf("%p(%d)\n%p(%d)\n", nx, nx, ny, ny)

	fmt.Println("函数参数")
	verbose(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)

	fmt.Println("结构体")
	var n1 Number
	n1.Print()
	fmt.Printf("n1: %p\n", &n1)
	n2 := &Number{}
	n2.Print()
	fmt.Printf("n2: %p\n", &n2)

	fmt.Println("接口")
	var p1 Printable
	p1 = n2
	fmt.Printf("p1: %p\n", &p1)
	p1.Print()

	//a2 := uintptr(unsafe.Pointer(n2))

	// TODO 根据sys.ArchFamilyType确定指针长度。
	base := uintptr(unsafe.Pointer(&p1))
	for i := 0; i < 64; i++ {
		b := (*byte)(unsafe.Pointer(base + uintptr(i)))
		fmt.Printf("%02x ", *b)
		if (i+1)%8 == 0 {
			fmt.Println()
		}
	}

	{
		var x *int
		x = F1()
		fmt.Printf("%p\n", x)
		x = F2()
		fmt.Printf("%p\n", x)
	}
}

type Printable interface {
	Print()
}

type Number struct {
	Value float64
}

func (r *Number) Print() {
	fmt.Printf("r: %p\n", r)
	fmt.Printf("&Value: %p\n", &r.Value)
}

func verbose(a, b, c, d, e, f, g, h, i, j, k, l int) {
	fmt.Printf("&a: %v\n", &a)
	fmt.Printf("&b: %v\n", &b)
	fmt.Printf("&c: %v\n", &c)
	fmt.Printf("&d: %v\n", &d)
	fmt.Printf("&e: %v\n", &e)
	fmt.Printf("&f: %v\n", &f)
	fmt.Printf("&g: %v\n", &g)
	fmt.Printf("&h: %v\n", &h)
	fmt.Printf("&i: %v\n", &i)
	fmt.Printf("&j: %v\n", &j)
	fmt.Printf("&k: %v\n", &k)
	fmt.Printf("&l: %v\n", &l)
}

func F1() *int {
	x := new(int)
	y := new(int)
	var z *int = x
	fmt.Printf("F1 x: %p y: %p\n", x, y)
	_ = z
	return y
}

func F2() *int {
	x := new(int)
	y := new(int)
	var z *int = x
	fmt.Printf("F2 x: %p y: %p\n", x, y)
	z = y
	_ = z
	return nil
}
