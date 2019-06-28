package main

func main() {
	c := make(chan bool)
	println(len(c), cap(c))
}
