/*
TODO 忽略Windows\Prefetch

*/
package main

import (
	"flag"
	"log"
	"os"
	"path/filepath"

	"github.com/fsnotify/fsnotify"
)

func main() {
	directory := ""
	flag.StringVar(&directory, "dir", ".", "watch directory")
	flag.Parse()

	watcher, err := fsnotify.NewWatcher()
	if err != nil {
		log.Fatal(err)
	}
	defer watcher.Close()

	err = watcher.Add(directory)
	if err != nil {
		log.Fatal(err)
	}

	filepath.Walk(directory, func(name string, info os.FileInfo, err error) error {
		if err != nil {
			log.Println("walk error:", err)
			return nil
		}

		if info.IsDir() {
			return nil
		}

		watcher.Add(filepath.Join(directory, name))
		return nil
	})

	log.Println("started.")

	done := make(chan bool)
	go watchLoop(watcher, done)

	<-done
}

func watchLoop(watcher *fsnotify.Watcher, done chan bool) {
	for {
		select {
		case event, ok := <-watcher.Events:
			if !ok {
				return
			}
			if event.Op&fsnotify.Create == fsnotify.Create {
				log.Println("created file:", event.Name)
				watcher.Add(event.Name)
			} else if event.Op&fsnotify.Write == fsnotify.Write {
				log.Println("modified file:", event.Name)
			}
		case err, ok := <-watcher.Errors:
			if !ok {
				return
			}
			log.Println("error:", err)
		}
	}
}
