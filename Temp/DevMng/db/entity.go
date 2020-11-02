package db

import (
	"time"
)

type LBMTableMapping struct {
	LBM       string
	TableName string
}

type BizLog struct {
	Time          time.Time
	IsRequest     bool
	ResultMessage string
	ResultCode    string
	SoftName      string
	Session       string
	SysVer        string
	UserCode      string
	MAC           string
	Path          string
	HWID          string
	Mobile        string
	IP            string
	NetAddr       string
	DeviceVers    string
	TraceID       string
	ParentID      string
	Service       string
	Method        string
	Raw           string
}
