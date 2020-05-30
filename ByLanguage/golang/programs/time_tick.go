package main

import (
	"log"
	"time"
)

func main() {
	cmd := make(chan int)
	tick := time.Tick(100 * time.Millisecond)
	running := true
	go f(cmd)

	for running {
		select {
		case x := <-tick:
			log.Println(x)
		case a, b := <-cmd:
			log.Println(a, b)
			running = false
			break
		}
	}
}

func f(cmd chan int) {
	time.Sleep(time.Second)
	close(cmd)
}
