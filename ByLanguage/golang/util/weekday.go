package util

import (
	"errors"
	"time"
)

var (
	ErrUnknownWeekday = errors.New("无法识别的工作日")
)

func ParseWeekday(s string) (time.Weekday, error) {
	switch s {
	case "星期一":
		return time.Monday, nil
	case "星期二":
		return time.Tuesday, nil
	case "星期三":
		return time.Wednesday, nil
	case "星期四":
		return time.Thursday, nil
	case "星期五":
		return time.Friday, nil
	case "星期六":
		return time.Saturday, nil
	case "星期日":
		return time.Sunday, nil

	default:
		return time.Sunday, ErrUnknownWeekday
	}
}

type WeekdayArray []time.Weekday

func (r WeekdayArray) Contains(w time.Weekday) bool {
	for _, wd := range r {
		if w == wd {
			return true
		}
	}
	return false
}
