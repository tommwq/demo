package main

import "fmt"

func sum (a []int, c chan int) {
	sum := 0
	for _, v := range a {
		sum += v
	}
	c <- sum
}

func main() {
	a := []int{7, 2, 8, -9, 4, 0}
	c := make(chan int)
	go sum(a[:len(a)/2], c)
	go sum(a[len(a)/2:], c)
	x, y := <- c, <-c
	fmt.Println(x, y, x + y)

	bufferedChannel()

	package main

	c := make(chan bool)
	println(len(c), cap(c))
}

func bufferedChannel() {
	ch := make(chan int, 2)
	ch <- 1
	ch <- 2
	fmt.Println(<-ch)
	fmt.Println(<-ch)

}


package main

func main() {
	defer println("bye")
	println("hello")

	ch := make(chan int)
	close(ch)
	v, ok := <-ch
	println(v, ok)
}

func panic1() {
	ch := make(chan int)
	close(ch)
	ch<- 1
}

func deadlock1() {
	var ch chan int
	ch<- 1
}
func deadlock2() {
	var ch chan int
	<-ch
}

