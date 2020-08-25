// Worker是一个工作调度示例。

// TODO 加入日志。
// TODO 将worker方法改为包私有。

package main

import (
	"flag"
	"fmt"
	"sync"
	"time"
)

var (
	debug = false
)

const (
	CommandExit = iota
	NoticeExited
	CommandTask
)

type Payload struct {
	Data interface{}
}

type Message struct {
	Type int
	Payload
}

type PayloadProcessor interface {
	Process(*Payload)
}

type worker struct {
	messageQueue chan *Message
	reportQueue  chan *Message
	dispatcher   *WorkDispatcher
	processor    PayloadProcessor
}

func newWorker(dispatcher *WorkDispatcher, processor PayloadProcessor) *worker {
	w := &worker{
		dispatcher: dispatcher,
		processor:  processor,
	}

	return w
}

func (r *worker) ReportReady() {
	if debug {
		fmt.Printf("Worker %p 报告就绪。\n", r)
	}
	r.dispatcher.ReportReady(r)
}

func (r *worker) Post(message *Message) {
	if debug {
		fmt.Printf("Worker %p 收到消息 %v。\n", r, message)
	}
	r.messageQueue <- message
}

func (r *worker) Start() {
	if debug {
		fmt.Printf("启动Worker %p。\n", r)
	}

	r.messageQueue = make(chan *Message)
	r.reportQueue = make(chan *Message)

	go func() {
		defer func() {
			if debug {
				fmt.Printf("Worker %p 退出。\n", r)
			}
		}()
		defer func() {
			r.reportQueue <- &Message{
				Type: NoticeExited,
			}
		}()

		for {
			r.ReportReady()
			message := <-r.messageQueue
			if debug {
				fmt.Printf("Worker %p 开始处理消息 %v。\n", r, message)
			}
			switch message.Type {
			case CommandExit:
				if debug {
					fmt.Printf("Worker %p 准备停止。\n", r)
				}
				return
			case CommandTask:
				if debug {
					fmt.Printf("Worker %p 开始处理任务 %v。\n", r, message.Payload)
				}
				r.processor.Process(&message.Payload)
				if debug {
					fmt.Printf("Worker %p 处理任务完毕，结果为 %v。\n", r, message.Payload)
				}
			}
		}
	}()
}

func (r *worker) Cleanup() {
	close(r.reportQueue)
	close(r.messageQueue)
	if debug {
		fmt.Printf("Worker %p 清理完毕。\n", r)
	}
}

func (r *worker) GetReport() *Message {
	m := <-r.reportQueue
	if debug {
		fmt.Printf("Worker %p 报告消息 %r\n", r, m)
	}
	return m
}

type WorkDispatcher struct {
	workerQueue  chan *worker
	workers      []*worker
	processor    PayloadProcessor
	workerNumber int
}

func NewWorkDispatcher(processor PayloadProcessor, workerNumber int) *WorkDispatcher {
	if debug {
		fmt.Printf("开始建立WorkDispatcher。\n")
	}

	d := &WorkDispatcher{
		processor:    processor,
		workerNumber: workerNumber,
	}

	if debug {
		fmt.Printf("建立WorkDispatcher完毕。\n")
	}
	return d
}

func (r *WorkDispatcher) ReportReady(w *worker) {
	r.workerQueue <- w
}

func (r *WorkDispatcher) Start() {
	if debug {
		fmt.Printf("开始启动WorkDispatcher。\n")
	}

	r.workerQueue = make(chan *worker)
	if len(r.workers) == 0 {
		r.workers = make([]*worker, r.workerNumber)

		for i := 0; i < r.workerNumber; i++ {
			r.workers[i] = newWorker(r, r.processor)
		}
	}

	for _, w := range r.workers {
		w.Start()
	}
	if debug {
		fmt.Printf("启动WorkDispatcher完毕。\n")
	}
}

func (r *WorkDispatcher) Stop() {
	if debug {
		fmt.Printf("开始关闭WorkDispatcher。\n")
	}

	wg := sync.WaitGroup{}
	wg.Add(len(r.workers))

	go func() {
		for {
			w := <-r.workerQueue
			if debug {
				fmt.Printf("停止Worker %p。\n", w)
			}
			w.Post(&Message{Type: CommandExit})
			if debug {
				fmt.Printf("等待Worker %p 报告。\n", w)
			}
			w.GetReport()
			if debug {
				fmt.Printf("清理Worker %p。\n", w)
			}
			w.Cleanup()
			wg.Done()
		}
	}()

	wg.Wait()

	close(r.workerQueue)

	if debug {
		fmt.Printf("关闭WorkDispatcher完毕。\n")
	}
}

func (r *WorkDispatcher) Post(message *Message) {
	w := <-r.workerQueue
	if debug {
		fmt.Printf("投递消息 %v 给 %v。\n", message, w)
	}
	w.Post(message)
}

type Adder struct {
	value int
}

func (r *Adder) Process(p *Payload) {
	value, ok := p.Data.(int)
	if !ok {
		return
	}

	p.Data = r.value + value
	time.Sleep(20 * time.Millisecond)
}

func NewAdder(value int) *Adder {
	return &Adder{
		value: value,
	}
}

func main() {
	var taskNumber int
	var processorNumber int

	flag.IntVar(&taskNumber, "task-number", 10, "task number")
	flag.IntVar(&processorNumber, "processor-number", 3, "processor number")
	flag.BoolVar(&debug, "debug", false, "debug mode")
	flag.Parse()

	processor := NewAdder(10)
	dispatcher := NewWorkDispatcher(processor, processorNumber)

	dispatcher.Start()
	for i := 0; i < taskNumber; i++ {
		message := &Message{
			Type: CommandTask,
			Payload: Payload{
				Data: i,
			},
		}
		dispatcher.Post(message)
	}

	dispatcher.Stop()
}
