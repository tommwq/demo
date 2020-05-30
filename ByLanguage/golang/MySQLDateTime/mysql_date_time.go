package main

import (
	"database/sql"
	"log"
	"time"

	_ "github.com/go-sql-driver/mysql"
)

/*
use wangqian_eqc;
DROP TABLE IF EXISTS test;
CREATE TABLE test (
  d DATE,
  t TIME,
  dt DATETIME
);
INSERT INTO test(d,t,dt) VALUES ("2017-01-01", "10:00:00", "2017-01-01 10:00:00");
*/

func main() {

	db, err := sql.Open("mysql", "root:5585354@tcp(172.24.180.69:3306)/wangqian_eqc?parseTime=true&charset=utf8")
	if err != nil {
		log.Fatal(err)
	}
	defer db.Close()

	rows, err := db.Query("SELECT dt FROM test LIMIT 10")
	if err != nil {
		log.Fatal(err)
	}
	defer rows.Close()

	var dt time.Time
	for rows.Next() {
		err = rows.Scan(&dt)
		if err != nil {
			log.Fatal(err)
		}
		log.Println(dt)
	}
	err = rows.Err()
	log.Println(err)
}
