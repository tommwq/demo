package main

import (
	"database/sql"
	"fmt"
	_ "github.com/go-sql-driver/mysql"
)

func main() {
	db, err := sql.Open("mysql", "root:5585354@(172.24.180.69:3306)/test")
	if err != nil {
		panic(err.Error())
	}
	defer db.Close()

	err = db.Ping()
	if err != nil {
		panic(err.Error())
	}
	
	rows, err := db.Query("SHOW TABLES")
	if err != nil {
		panic(err.Error())
	}
	fmt.Println(rows)
}
