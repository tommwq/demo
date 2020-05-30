package main

import (
	"log"
	"time"
)

type Ticker struct {
	start    TimePoint
	stop     TimePoint
	tick     chan time.Time
	last     time.Time
	duration time.Duration
}

func (r *Ticker) Tick() <-chan time.Time {
	return r.tick
}

func (r *Ticker) calculateSleepTime() time.Duration {
	now := time.Now()
	var sleepTime time.Duration

	if now.After(r.stop.Time(now)) {
		tomorrow := now.Add(24 * time.Hour)
		tomorrowStart := time.Date(tomorrow.Year(), tomorrow.Month(), tomorrow.Day(), r.start.Hour,
			r.start.Minute, 0, 0, time.Local)
		return tomorrowStart.Sub(now)
	}
	if !r.start.Time(now).Before(now) {
		sleepTime = r.start.Time(now).Sub(now)
		return sleepTime
	}

	nextTickTime := r.last.Add(r.duration)
	sleepTime = nextTickTime.Sub(now)
	return sleepTime
}

func (r *Ticker) sleepUntilNextTick() {
	sleepTime := r.calculateSleepTime()
	time.Sleep(sleepTime)
}

func (r *Ticker) cycle() {
	for {
		r.sleepUntilNextTick()
		r.last = time.Now()
		r.tick <- r.last
	}
}

func NewTicker(start, stop TimePoint, duration time.Duration) *Ticker {
	ticker := &Ticker{
		tick:     make(chan time.Time),
		start:    start,
		stop:     stop,
		duration: duration,
		last:     time.Now().Truncate(time.Second),
	}

	go ticker.cycle()

	return ticker
}

type TimePoint struct {
	Hour   int
	Minute int
}

func (r TimePoint) Time(today time.Time) time.Time {
	return time.Date(today.Year(), today.Month(), today.Day(), r.Hour, r.Minute, 0, 0, time.Local)
}

func NewTimePoint(day time.Time) TimePoint {
	return TimePoint{
		Hour:   day.Hour(),
		Minute: day.Minute(),
	}
}

func main() {
	log.SetFlags(log.LstdFlags | log.Lmicroseconds)

	start := TimePoint{11, 30}
	stop := TimePoint{13, 54}

	tick := NewTicker(start, stop, time.Second).Tick()

	for t := range tick {
		log.Println(t)
	}
}
