package main

import (
	"fmt"
	"strconv"
	"time"
)

func main() {
	var (
		start time.Time
		stop  time.Time
		a     = 12.34
		count = 10000 * 100
	)

	start = time.Now()
	for i := 0; i < count; i++ {
		strconv.FormatFloat(a, 'f', 2, 64)
	}
	stop = time.Now()
	fmt.Println(stop.Sub(start))

	start = time.Now()
	for i := 0; i < count; i++ {
		fmt.Sprintf("%.2f", a)
	}
	stop = time.Now()
	fmt.Println(stop.Sub(start))
}
