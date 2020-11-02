package main

import (
	"fmt"
)

type Config struct {
	LogParsers []struct {
		LogDirectory string
		LogFileName  string
		ParserName   string
	}
	Database struct {
		Host     string
		Driver   string // mysql/sqlserver
		Port     int
		User     string
		Password string
		Database string
	}
}

func (r Config) GetDSN() string {
	switch r.Database.Driver {
	case "sqlserver":
		return fmt.Sprintf("sqlserver://%s:%s@%s:%d?database=%s&",
			r.Database.User,
			r.Database.Password,
			r.Database.Host,
			r.Database.Port,
			r.Database.Database)
	default:
		panic(r.Database.Driver)
	}
}
