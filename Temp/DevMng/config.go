package main

import (
	"fmt"
)

type Database struct {
	Host     string
	Port     int
	User     string
	Password string
	Driver   string
	Database string
}

func (r Database) DSN() string {

	return fmt.Sprintf("%v:%v@tcp(%v:%v)/%v?charset=utf8mb4&parseTime=True&loc=Local",
		r.User,
		r.Password,
		r.Host,
		r.Port,
		r.Database)
}

type Config struct {
	Machines []Machine
	Database Database
}
