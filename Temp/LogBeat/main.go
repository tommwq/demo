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
	"time"

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
	// TODO GetMobile ...
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
	sql := fmt.Sprintf("insert into T_QUICK_LOCATE_REQUEST_DETAIL_gstrade_%s (COMPONENT, TRACE_ID, REQ_TIME, REQ_DATE, REQ_METHOD, REQ_CONTENT) VALUES ('%v','%v','%v','%v','%v','%v')",
		time.Now().Format("20060102"),
		"gsbp",
		"0",
		bl.Time,
		time.Now().Format("20060102"),
		bl.Method,
		fmt.Sprintf("%v", bl))
	result, err := database.Exec(sql)

	log.Println(result, err)
	sql = fmt.Sprintf("insert into T_QUICK_LOCATE_REQUEST_gstrade_%s (COMPONENT, CLUSTER_NAME, HOST_NAME, TRACE_ID, REQ_METHOD, PKG, MOBILE, FUND_ID, SOFT_NAME, SOFT_VERSION, REQ_TIME, CLIENT_REQ_TIME, REQ_DATE, MEMORY_USER, INPUT_TYPE) VALUES('%v', '%v', '%v', '%v', '%v', '%v', '%v', '%v', '%v', '%v', '%v', '%v', '%v', '%v', '%v')",
		time.Now().Format("20060102"),
		"gsbp",
		"cluster_0",
		"host_0",
		"trace_id_0",
		bl.Method,
		"pkg_0",
		"1234567",
		"fund_id",
		"android_ios",
		"5.4.3.2.1",
		bl.Time,
		bl.Time,
		time.Now().Format("20060102"),
		"0",
		"1")
	result, err = database.Exec(sql)
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
