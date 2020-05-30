package main

import (
	"fmt"
	"net/http"
	"os"
	"time"
)

func main() {
	count := len(os.Args)
	messages := make(chan interface{}, count)

	start := time.Now()
	for i := 1; i < count; i++ {
		go fetch(os.Args[i], messages)
	}

	for i := 1; i < count; i++ {
		result, _ := <-messages
		elapsed := time.Since(start).Seconds()
		fmt.Printf("%.2fs %v\n", elapsed, result)
	}

	fmt.Printf("%.2fs\n", time.Since(start).Seconds())
}

func fetch(url string, messages chan interface{}) {
	response, err := http.Get(url)
	if err != nil {
		messages <- err
		return
	}
	response.Body.Close()
	messages <- url
}
