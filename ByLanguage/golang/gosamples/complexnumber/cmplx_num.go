package main

import (
	"math"
	"math/cmplx"
)

func main() {
	scale := -10.0
	degree := 60.0
	radian := degree / 180.0 * math.Pi

	var a, b, c complex128

	a = complex(scale*math.Cos(radian), scale*math.Sin(radian))
	b = complex(3.0, 0.0)
	c = cmplx.Pow(a, b)
	println(c)

	a = complex(scale*math.Cos(radian), -1.0*scale*math.Sin(radian))
	c = cmplx.Pow(a, b)
	println(c)

	a = complex(10.0, 0.0)
	c = cmplx.Pow(a, b)
	println(c)
	
	a = complex(-1000.0, 0.0)
	b = complex(1.0/3.0, 0.0)
	c = cmplx.Pow(a, b)
	println(c)

	a = complex(1000.0, 0.0)
	c = cmplx.Pow(a, b)
	println(c)
}
