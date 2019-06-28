package main

// TODO 优化对大文件的处理。

import (
	"crypto/md5"
	"encoding/binary"
	"flag"
	"fmt"
	"io"
	"io/ioutil"
	"log"
	"os"
	"path/filepath"
	"strconv"
	"time"

	"github.com/howeyc/fsnotify"
)

var (
	debugMode bool
)

func main() {
	showVersion := flag.Bool("version", false, "显示程序版本信息。")
	showHelp := flag.Bool("help", false, "显示帮助信息。")
	watchFile := flag.String("record", "", "要监控的文件。")
	outputFile := flag.String("save-to", "", "输出文件。")
	recordTime := flag.String("record-time", "", "记录时间。单位：秒。")
	recordFile := flag.String("replay", "", "要回放的文件。")
	flag.BoolVar(&debugMode, "debug", false, "调试模式。")
	flag.Parse()

	log.SetFlags(log.LstdFlags | log.Lshortfile)

	if *showVersion {
		program := filepath.Base(os.Args[0])
		fmt.Printf("%s\n版本: %v, 发布日期: %v\n", program, version, releaseDate)
		return
	}

	if *showHelp {
		program := filepath.Base(os.Args[0])
		helpMessage := `帮助信息

记录文件变更：
  %s -record FILE -save-to RECORD_FILE -record-time SECONDS

回放文件变更
  %s -replay RECORD_FILE -save-to OUTPUT_FILE

参数:
`
		fmt.Printf(helpMessage, program, program)
		flag.PrintDefaults()
		return
	}

	var err error
	switch {
	case *watchFile != "":
		filename := *watchFile
		output := *outputFile
		seconds, _ := strconv.Atoi(*recordTime)
		duration := time.Duration(seconds) * time.Second
		err = watch(filename, output, duration)
	case *recordFile != "":
		filename := *recordFile
		output := *outputFile
		err = replay(filename, output)
	}

	if err != nil {
		log.Println(err)
	}
}

// Snapshot 文件快照
type Snapshot struct {
	// 时间偏移量，单位毫秒。
	TimeOffset int64
	// 文件内容。
	Data []byte
}

// appendSnapshot 读取sourceFile文件内容，生成快照，追加写入file
// file 快照记录文件
// start 开始建立快照的时间
// sourceFile 源文件
func appendSnapshot(file *os.File, start int64, sourceFile string) error {
	now := time.Now().Unix() - start

	content, err := ioutil.ReadFile(sourceFile)
	if err != nil {
		return err
	}

	appendSnapshotContent(file, start, now, content)

	return nil
}

// TODO 去掉全局变量prevMD5。
var prevMD5 string

// MD5SumString 返回数据的MD5字符串
func MD5SumString(data []byte) string {
	sum := md5.Sum(data)
	return fmt.Sprintf("%x", sum[:])
}

// appendSnapshotContent 将快照content，追加写入file
// file 快照记录文件
// start 开始建立快照的时间
// now 当前时间
// content 快照内容
func appendSnapshotContent(file *os.File, start, now int64, content []byte) error {
	sum := MD5SumString(content)
	if sum == prevMD5 {
		return nil
	}

	var err error
	if err = binary.Write(file, binary.LittleEndian, now); err != nil {
		return err
	}

	length := int32(len(content))
	if err = binary.Write(file, binary.LittleEndian, length); err != nil {
		return err
	}

	if err = binary.Write(file, binary.LittleEndian, content); err != nil {
		return err
	}

	prevMD5 = sum
	if debugMode {
		length := len(content)
		if length > 20 {
			length = 20
		}
		log.Println("APPEND " + string(content[:length]))
	}

	return nil
}

// watch 监控文件filename，监控时长为duration。将结果写入output
func watch(filename, output string, duration time.Duration) error {
	log.Printf("开始监控文件%s，持续%d秒。\n", filename, int(duration/time.Second))
	defer func(filename string) {
		log.Printf("监控文件%s结束。\n", filename)
	}(filename)

	watcher, err := fsnotify.NewWatcher()
	if err != nil {
		return err
	}

	directory := filepath.Dir(filename)
	source := filepath.Base(filename)

	outputFile, err := os.OpenFile(output, os.O_WRONLY|os.O_TRUNC|os.O_CREATE, os.ModePerm)
	if err != nil {
		return err
	}
	defer outputFile.Close()

	start := time.Now().Unix()
	if err = binary.Write(outputFile, binary.LittleEndian, start); err != nil {
		return err
	}
	if err = appendSnapshot(outputFile, start, filename); err != nil {
		return err
	}

	go func() {
		for {
			select {
			case ev := <-watcher.Event:
				if ev == nil {
					break
				}
				if !ev.IsModify() {
					continue
				}
				basename := filepath.Base(ev.Name)
				if basename != source {
					continue
				}
				if er := appendSnapshot(outputFile, start, filename); er != nil {
					log.Println("写入记录文件错误", er)
				}
			case er := <-watcher.Error:
				log.Println("未知错误", er)
			}
		}
	}()

	err = watcher.Watch(directory)
	if err != nil {
		return err
	}

	time.Sleep(duration)
	watcher.Close()

	return nil
}

// readSnapshot 读取一个文件快照
func readSnapshot(record *os.File) (*Snapshot, error) {
	var timeOffset int64
	var length int32

	if err := binary.Read(record, binary.LittleEndian, &timeOffset); err != nil {
		return nil, err
	}
	if err := binary.Read(record, binary.LittleEndian, &length); err != nil {
		return nil, err
	}

	data := make([]byte, length)
	if err := binary.Read(record, binary.LittleEndian, data); err != nil {
		return nil, err
	}

	return &Snapshot{
		TimeOffset: timeOffset,
		Data:       data,
	}, nil
}

// replay 回放文件filename到文件output
func replay(filename, output string) error {
	log.Printf("开始回放文件%s。\n", filename)
	defer func(filename string) {
		log.Printf("回放文件%s结束。\n", filename)
	}(filename)

	file, err := os.OpenFile(filename, os.O_RDONLY, os.ModePerm)
	if err != nil {
		return err
	}
	defer file.Close()

	var start int64
	if err = binary.Read(file, binary.LittleEndian, &start); err != nil {
		return err
	}

	log.Println("文件记录时间", time.Unix(start, 0))
	var prev int64 = 0
	var offset int64 = 0

	for {
		snapshot, err := readSnapshot(file)
		if err == io.EOF {
			return nil
		} else if err != nil {
			return err
		}

		offset = snapshot.TimeOffset
		time.Sleep(time.Duration(offset-prev) * time.Second)
		//log.Println("时间", snapshot.TimeOffset, "数据", string(snapshot.Data))
		if err = ioutil.WriteFile(output, snapshot.Data, os.ModePerm); err != nil {
			return err
		}
		prev = offset

		if debugMode {
			content := snapshot.Data
			length := len(content)
			if length > 20 {
				length = 20
			}
			log.Println("APPEND " + string(content[:length]))
		}
	}

	return nil
}
