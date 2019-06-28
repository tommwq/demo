package util

import (
	"errors"
	"strings"
	"time"
)

var (
	ErrInvalidTimeFormat = errors.New("非法的时间格式")
)

// TimeRange 表示一天之内的一个时间段，[Begin, End]。单位是秒。
type TimeRange struct {
	// 从当天零时开始经过的秒数
	BeginTime time.Time
	EndTime   time.Time
}

// Parse 解析类似"09:30-11:30"的字符串。
func (r *TimeRange) Parse(s string) error {
	blocks := strings.Split(s, "-")
	if len(blocks) != 2 {
		return ErrInvalidTimeFormat
	}

	format := "15:04"

	start, err := time.Parse(format, blocks[0])
	if err != nil {
		return ErrInvalidTimeFormat
	}

	stop, err := time.Parse(format, blocks[1])
	if err != nil {
		return ErrInvalidTimeFormat
	}

	if start.After(stop) {
		return ErrInvalidTimeFormat
	}

	r.BeginTime = start
	r.EndTime = stop

	return nil
}

// Contains 判断TimeRange是否包含t
func (r TimeRange) Contains(t time.Time) bool {
	h := t.Hour()
	m := t.Minute()
	s := t.Second()

	x := time.Date(0, time.Month(1), 1, h, m, s, 0, time.UTC)

	if x.Before(r.BeginTime) || x.After(r.EndTime) {
		return false
	}

	return true
}

// TimeRangeArray 表示一个TimeRange数组
type TimeRangeArray []TimeRange

// Contains 判断TimeRangeArray是否包含t
func (r TimeRangeArray) Contains(t time.Time) bool {
	for _, tr := range r {
		if tr.Contains(t) {
			return true
		}
	}
	return false
}
