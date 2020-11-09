package main

// TODO 处理文件CREATE事件

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
	Request     interface{} `json:"req"`
	Rsponse     interface{} `json:"rsp"`
	RPCContext  interface{} `json:"rpcContext"`
	Attachments interface{} `json:"attachments"`
	// TODO GetMobile ...
}

func (r BizLog) IsRequest() bool {
	return len(r.Request) > 0
}

func (r BizLog) IsResponse() bool {
	return len(r.Response) > 0
}

func (r BizLog) ResultMSG() string {
	result, ok := r.Response["result"]
	if !ok {
		return ""
	}
	if len(result) == 0 {
		return ""
	}
	message, ok := result[0]["msg"]
	if !ok {
		return ""
	}
	return message
}

func (r BizLog) ResultCode() string {
	result, ok := r.Response["result"]
	if !ok {
		return ""
	}
	if len(result) == 0 {
		return ""
	}
	code, ok := result[0]["code"]
	if !ok {
		return ""
	}
	return code
}

func (r BizLog) ResultData() map[string]string {
	data, ok := r.Response["data"]
	if !ok {
		return make(map[string]string)
	}
	if len(data) == 0 {
		return make(map[string]string)
	}
	return data[0]
}

func (r BizLog) Context(key string) *string {
	m, ok := r.RPCContext.(map[string]string)
	if !ok {
		return nil
	}
	v, ok := m[key]
	if !ok {
		return nil
	}
	return &v
}

func (r BizLog) Attachment(key string) *string {
	m, ok := r.Attachments.(map[string]string)
	if !ok {
		return nil
	}
	v, ok := m[key]
	if !ok {
		return nil
	}
	return &v
}

func (r BizLog) SoftName() *string {
	return r.Attachment("softName")
}

func (r BizLog) Session() *string {
	return r.Attachment("session")
}
func (r BizLog) SysVer() *string {
	return r.Attachment("sysVer")
}
func (r BizLog) UserCode() *string {
	return r.Attachment("userCode")
}
func (r BizLog) MAC() *string {
	return r.Attachment("mac")
}
func (r BizLog) Path() *string {
	return r.Attachment("path")
}
func (r BizLog) HWID() *string {
	return r.Attachment("hwID")
}
func (r BizLog) Mobile() *string {
	return r.Attachment("mobile")
}
func (r BizLog) IP() *string {
	return r.Attachment("ip")
}
func (r BizLog) NetAddr() *string {
	return r.Attachment("netAddr")
}
func (r BizLog) DeviceVers() *string {
	return r.Attachment("deviceVers")
}
func (r BizLog) TraceID() *string {
	return r.Context("traceId")
}
func (r BizLog) ParentID() *string {
	return r.Context("parentId")
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
