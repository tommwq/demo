package util

import (
	"errors"
	"fmt"
	"strings"
)

var (
	ErrUnsupportedDB = errors.New("不支持的数据库类型")
)

type DatabaseConfig struct {
	Type     string
	Host     string
	Port     int
	User     string
	Password string
	Database string
}

var (
	sqlserverDSNFormat = "user id=%v;password=%v;server=%v;port=%v;database=%v;%v"
	mysqlDSNFormat     = "%v:%v@tcp(%v:%v)/%v?%v"
)

func (r *DatabaseConfig) DSN() (string, error) {
	var format string
	var param string
	switch strings.ToLower(r.Type) {
	case "mssql", "sqlserver":
		format = sqlserverDSNFormat
		param = "encrypt=disable;packet size=4096;"
	case "mysql":
		format = mysqlDSNFormat
		param = "parseTime=true"
	default:
		return "", ErrUnsupportedDB
	}

	return fmt.Sprintf(format, r.User, r.Password, r.Host, r.Port, r.Database, param), nil
}
