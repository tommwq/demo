//
// sf.go
// service framework demo
//
// Create: 2016-08-19
// Modified: 2016-08-19
//

package main

import (
	"fmt"
	"log"
	"math/rand"
	"time"
)

type PayLoad struct {
	Id uint64
	Content string
}

func main() {
	rand.Seed(time.Now().UnixNano())
	
	input := make(chan PayLoad)
	output := make(chan PayLoad)
	command := make(chan int)

	for i := 0; i < 5; i++ {
		go simulateClient(i, input, command)
	}
	go timer(100, command)

	for i := 0; i < 3; i++ {
		go handle(i, input, output, command)
	}

	for running := true; running; {
		select {
		case out := <-output:
			log.Println(out)
		case _, ok := <-command:
			if !ok {
				running = false
			}
		}
	}
}

func process(payload PayLoad) PayLoad {
	return PayLoad{
		Id: payload.Id,
		Content: fmt.Sprintf("OK"),
	}
}

func handle(id int, input, output chan PayLoad, command chan int) {
	for running := true; running; {
		output <- process(<-input)

		select {
		case _, ok := <-command:
			if !ok {
				running = false
			}
		default:
			break
		}
	}
}

func timer(ms int, command chan int) {
	time.Sleep(time.Duration(ms) * time.Millisecond)
	close(command)
}

func simulateClient(id int, input chan PayLoad, command chan int) {
	for running := true; running; {
		t := rand.Int() % 10
		time.Sleep(time.Duration(t) * time.Millisecond)

		input <- PayLoad {
			Id: uint64(t),
			Content: fmt.Sprintf("PayLoad %v", t),
		}

		select {
		case _, ok := <-command:
			if !ok {
				running = false
			}
		default:
			break
		}
	}
}
