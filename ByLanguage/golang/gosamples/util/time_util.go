package util

import (
	"time"
)

func IsSameDay(t1, t2 time.Time) bool {
	y, m, d := t1.Date()
	zero := time.Date(y, m, d, 0, 0, 0, 0, time.Local)
	if zero.After(t2) {
		return false
	}

	if t2.After(zero.AddDate(0, 0, 1)) {
		return false
	}

	return true
}

func ZeroTime(l *time.Location, t time.Time) time.Time {
	y, m, d := t.Date()
	return time.Date(y, m, d, 0, 0, 0, 0, l)
}

func SleepToTomorrow(now time.Time) {
	sleepTime := ZeroTime(time.Local, now).AddDate(0, 0, 1).Sub(now)
	time.Sleep(sleepTime)
}
