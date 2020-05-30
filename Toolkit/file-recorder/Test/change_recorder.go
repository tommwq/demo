// change_recorder.go
// 测试FileRecorder使用的工具。
// 启动后，ChangeRecorder会监控a.txt，当a.txt文件内容发生变化时，将其内容写入record.txt文件。

package main

import (
	"crypto/md5"
	"io/ioutil"
	"log"
	"os"
	"path/filepath"
	"strings"
	"time"

	"github.com/howeyc/fsnotify"
)

func main() {
	watcher, err := fsnotify.NewWatcher()
	if err != nil {
		log.Fatalln(err)
	}

	list := make([]string, 0)

	go func() {
		prev := ""
		for {
			select {
			case ev := <-watcher.Event:
				if ev == nil {
					continue
				}
				if !ev.IsModify() {
					continue
				}
				basename := filepath.Base(ev.Name)
				if basename != "a.txt" {
					continue
				}
				data, err := ioutil.ReadFile("a.txt")
				if err != nil {
					log.Fatalln(err)
				}
				s := md5.Sum(data)
				sum := string(s[:])
				if sum != prev {
					prev = sum
					list = append(list, string(data))
				}
			case <-watcher.Error:
				break
			}
		}
	}()

	err = watcher.Watch(".")
	if err != nil {
		log.Fatalln(err)
	}

	time.Sleep(7 * time.Second)
	watcher.Close()

	text := strings.Join(list, "\n")
	err = ioutil.WriteFile("record.txt", []byte(text), os.ModePerm)
	if err != nil {
		log.Fatalln(err)
	}
}
