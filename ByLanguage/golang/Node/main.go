package main

import (
	"log"
	"sync"
	"time"
)

func main() {
	log.Println("开始执行程序。")

	manager := NewNodeManager()
	manager.StartNode("print", 20, &Printer{})

	wg := sync.WaitGroup{}
	wg.Add(100)

	for i := 0; i < 100; i++ {
		go func(i int) {
			manager.Post("print", &Payload{"", i})
			wg.Done()
		}(i)
	}

	wg.Wait()
	time.Sleep(300 * time.Millisecond)


	log.Println("执行程序完毕。")
}
