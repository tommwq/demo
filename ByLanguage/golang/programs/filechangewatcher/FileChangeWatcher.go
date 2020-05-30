// FileChangeWatcher.go
// 监控文件写入事件，打印时间和文件名。
// 建立日期：2016年12月28日
// 最后编辑：2016年12月28日

package main

import (
	"fmt"
	"github.com/howeyc/fsnotify"
	"log"
	//	"time"
	"flag"
	"os"
	"path"
)

const (
	VersionString     = "1.00"
	ReleaseDateString = "2016.12.28"
)

func main() {
	log.SetFlags(log.LstdFlags | log.Lshortfile)
	watchPath := flag.String("path", ".", "file or directory to watch")
	version := flag.Bool("version", false, "show version")
	flag.Parse()

	if *version {
		fmt.Printf("version: %s release: %s\n", VersionString, ReleaseDateString)
		return
	}

	fileInfo, err := os.Lstat(*watchPath)
	if err != nil {
		log.Fatalln(err)
	}

	directory := *watchPath
	if !fileInfo.IsDir() {
		directory = path.Dir(directory)
	}

	watcher, err := fsnotify.NewWatcher()
	if err != nil {
		log.Fatalln(err)
	}

	err = watcher.Watch(directory)
	if err != nil {
		log.Fatal(err)
	}

	for {
		select {
		case ev := <-watcher.Event:
			log.Println(ev)
		case err := <-watcher.Error:
			log.Println("error:", err)
		}
	}
}
