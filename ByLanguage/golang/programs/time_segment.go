package main

import (
	"log"
	"strings"
	"time"
)

func main() {
	timeSegment := "15:29-15:40"
	segments := strings.Split(timeSegment, "-")
	s1 := segments[0]
	s2 := segments[1]
	start, e1 := time.Parse("15:04", s1)
	stop, e2 := time.Parse("15:04", s2)

	_ = e1
	_ = e2

	now := time.Now()
	hour := now.Hour()
	minute := now.Minute()
	current := time.Date(0, time.Month(1), 1, hour, minute, 0, 0, time.UTC)
	log.Println(current, start, stop)

	// [start, stop)
	if current.Before(stop) && !start.After(current) {
		log.Println("IN")
	} else {
		log.Println("OUT")
	}
}
