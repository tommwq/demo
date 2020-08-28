package main

const (
	a = 1
	b
	c
)

const (
	x = iota
	y
	z
)

type Color int
const (
	black Color = iota
	white
)

type Flags uint
const (
	FlagUp Flags = 1 << iota
	FlagBroadcast
	FlagLoopback
)

const (
	_ = 1 << (10 * iota)
	KiB
	MiB
	GiB
)

func main() {
	println(a, b, c)
	println(x, y, z)
	println(black, white)
	println(FlagUp, FlagBroadcast, FlagLoopback)
	println(KiB, MiB, GiB)
}
