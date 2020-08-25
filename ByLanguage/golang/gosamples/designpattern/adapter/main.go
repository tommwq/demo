package main

func main() {

	origin := new(Origin)
	adapter1 := NewAdapter1(origin)
	adapter1.SayHello()

	adapter2 := NewAdapter2(origin)
	adapter2.SayHello()
}
