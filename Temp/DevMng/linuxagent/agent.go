package main

import (
	"database/sql"
	"encoding/json"
	"fmt"
	"io/ioutil"
	"log"

	"github.com/go-sql-driver/mysql"
)

type Config struct {
	Database struct {
		Host     string
		Port     int
		User     string
		Password string
		Database string
	}
	BusinessLogPath string
	DebugLogPath    string
}

func main() {
	config := Config{}
	if data, err := ioutil.ReadFile("./config.json"); err != nil {
		log.Fatal(err)
	} else if err = json.Unmarshal(data, &config); err != nil {
		log.Fatal(err)
	}

	mysqlConfig := mysql.Config{
		User:                 config.Database.User,
		Passwd:               config.Database.Password,
		DBName:               config.Database.Database,
		AllowNativePasswords: true,
		Net:                  "tcp",
		Addr:                 fmt.Sprintf("%s:%d", config.Database.Host, config.Database.Port),
	}

	log.Println(config)

	db, err := sql.Open("mysql", mysqlConfig.FormatDSN())
	if err != nil {
		log.Fatal(err)
	}
	defer db.Close()

	result, err := db.Exec("select 1")
	if err != nil {
		log.Fatal(err)
	}
	log.Println(result)
}
