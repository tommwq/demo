package main

import (
	"flag"
	"log"
	"sync"
	"time"
)

func main() {
	var function string
	flag.StringVar(&function, "function", "ok", "function to run")
	flag.Parse()

	switch function {
	default:
		fallthrough
	case "ok":
		ok()
	case "deadlock":
		deadlock()
	}
}

func ok() {
	wg := &sync.WaitGroup{}
	wg.Add(1)

	go func(wg *sync.WaitGroup) {
		time.Sleep(1000)
		wg.Done()
	}(wg)

	log.Println("WAITING...")
	wg.Wait()
	log.Println("OVER")

}

func deadlock() {
	wg := &sync.WaitGroup{}
	wg.Add(1)

	log.Println("WAITING...")
	wg.Wait()
	log.Println("OVER")
}
