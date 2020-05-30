// random_writer.go
// 测试FileRecorder使用的工具。
// 启动后，RandomWriter会创建文件a.txt和change.txt。每隔200-500毫秒，将a.txt的内容修改为一个随机字符串。
// 并将这些字符串依次写入change.txt。一共写10次。

package main

import (
	"crypto/md5"
	"fmt"
	"io/ioutil"
	"log"
	"math/rand"
	"os"
	"strings"
	"time"
)

func generateData() string {
	size := len(text)
	begin := rand.Intn(size + 1)
	end := rand.Intn(size + 1)

	if end == begin {
		end = (begin + 1) % (size + 1)
	}
	if end < begin {
		begin, end = end, begin
	}

	return fmt.Sprintf("%x", md5.Sum([]byte(text[begin:end])))
}

func main() {
	rand.Seed(time.Now().UnixNano())

	snapshots := make([]string, 10)
	for i := 0; i < len(snapshots); i++ {
		snapshot := generateData()
		snapshots[i] = snapshot
		err := ioutil.WriteFile("a.txt", []byte(snapshot), os.ModePerm)
		if err != nil {
			log.Fatalln(err)
		}
		t := 200 + rand.Intn(300)
		time.Sleep(time.Duration(t) * time.Millisecond)
	}

	err := ioutil.WriteFile("change.txt", []byte(strings.Join(snapshots, "\n")), os.ModePerm)
	if err != nil {
		log.Fatalln(err)
	}
}

const text = `".. and you, Marcus, you have given me many things; now I shall give your this good advice. Be many people, Give up the game of being always Marcus Cocoza. You have worried too much about Marcus Cocoza, so that you have been really his salve and prisoner. You have not done anything without first considering how it would affect Marcus Cocoza's happiness and prestidge. You were always much afraid that Marcus might do a stupid thing, or be bored. What would it really have mattered? All over the world people are doing stupid things ... I  should like you to be easy, your little heart to be light again. you must from now, be more than one, many people. as many as you can think of ..."

- Karen Blixen                                               
("The Dreamers" from "Server Gothic Tales"        
  written under the preudonym Isak Dinesen,      
Random House, Inc.                                      
Copyright, Isak Dinesen, 1934 renewed 1961) 

Copy from the "The C++ Programming Language" Special Version.`
