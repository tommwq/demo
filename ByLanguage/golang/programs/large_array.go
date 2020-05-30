package main

import (
	"fmt"
	"os"
	"os/signal"
)

func main() {
	count := 10000 * 50
	a := make([]string, count)

	for i := 0; i < count; i++ {
		a[i] = fmt.Sprintf("%09d", i)
	}

	c := make(chan os.Signal, 1)
	signal.Notify(c, os.Interrupt)
	<-c
}
