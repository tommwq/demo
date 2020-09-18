package main

import (
	"database/sql"
	"encoding/json"
	"fmt"
	"io"
	"io/ioutil"
	"log"
	"os"
	"path/filepath"

	"github.com/fsnotify/fsnotify"

	_ "github.com/denisenkom/go-mssqldb"
	_ "github.com/go-sql-driver/mysql"
)

type BizLog struct {
	Time        string      `json:"time"`
	Service     string      `json:"service"`
	Method      string      `json:"method"`
	Req         interface{} `json:"req"`
	Rsp         interface{} `json:"rsp"`
	RPCContext  interface{} `json:"rpcContext"`
	Attachments interface{} `json:"attachments"`
}

func collectNewData(file *os.File) {
	buffer := make([]byte, 1024*1024)
	size, err := file.Read(buffer)
	if err != nil && err != io.EOF {
		log.Fatal(err)
	}
	bl := BizLog{}
	err = json.Unmarshal(buffer[:size], &bl)
	if err != nil {
		log.Fatal(err)
	}
	log.Print(bl)

	sql := fmt.Sprintf("insert into T_QUICK_LOCATE_REQUEST_DETAIL_gstrade_20200918 (COMPONENT, TRACE_ID, REQ_TIME, REQ_DATE, REQ_METHOD, REQ_CONTENT) VALUES ('%v','%v','%v','%v','%v','%v')",
		"gsbp",
		"0",
		bl.Time,
		20200918, // bl.Time,
		bl.Method,
		fmt.Sprintf("%v", bl))
	result, err := database.Exec(sql)
	log.Println(result, err)
}

var (
	config   Config
	database *sql.DB
)

func main() {
	configContent, err := ioutil.ReadFile("./config.js")
	if err != nil {
		log.Fatal(err)
	}
	err = json.Unmarshal(configContent, &config)
	if err != nil {
		log.Fatal(err)
	}

	database, err = sql.Open(config.Database.Driver, config.GetDSN())
	if err != nil {
		log.Fatal(err)
	}
	defer database.Close()

	err = database.Ping()
	if err != nil {
		log.Fatal(err)
	}

	fileName := filepath.Join(config.LogParsers[0].LogDirectory, config.LogParsers[0].LogFileName)
	file, err := os.OpenFile(fileName, os.O_RDONLY, os.ModePerm)
	if err != nil {
		log.Fatal(err)
	}
	defer file.Close()

	file.Seek(0, io.SeekEnd)

	watcher, err := fsnotify.NewWatcher()
	if err != nil {
		log.Fatal(err)
	}
	defer watcher.Close()

	watcher.Add(fileName)

	for {
		select {
		case event := <-watcher.Events:
			if event.Op == fsnotify.Write {
				collectNewData(file)
			}
		case err := <-watcher.Errors:
			log.Println(err)
		}
	}
}
