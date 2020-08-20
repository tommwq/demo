package util

import (
	"errors"
	"strings"
	"time"
)

var (
	ErrInvalidDateFormat = errors.New("非法的时间格式")
)

// DateRange 表示两个日期之间的时间段，[Begin, End]。
type DateRange struct {
	BeginDate time.Time
	EndDate   time.Time
}

// Parse 解析类似"20171001-20171002"的字符串。
func (r *DateRange) Parse(s string) error {
	format := "20060102"

	blocks := strings.Split(s, "-")
	l := len(blocks)
	if l > 2 || l == 0 {
		return ErrInvalidDateFormat
	}

	begin, err := time.Parse(format, blocks[0])
	if err != nil {
		return ErrInvalidDateFormat
	}

	if l == 1 {
		r.BeginDate = begin
		r.EndDate = begin
		return nil
	}

	end, err := time.Parse(format, blocks[1])
	if err != nil {
		return ErrInvalidDateFormat
	}
	r.BeginDate = begin
	r.EndDate = end
	return nil
}

func (r DateRange) Contains(date time.Time) bool {
	y, m, d := date.Date()
	day := time.Date(y, m, d, 0, 0, 0, 0, time.Local)
	if day.Before(r.BeginDate) || day.After(r.EndDate) {
		return false
	}
	return true
}

type DateRangeArray []DateRange

func (r DateRangeArray) Contains(date time.Time) bool {
	for _, dr := range r {
		if dr.Contains(date) {
			return true
		}
	}
	return false
}
