package main

import (
	"log"
	"sync"
	"time"
)

var (
	over = false
)

func main() {
	log.SetFlags(log.LstdFlags | log.Lmicroseconds)

	log.Println("BEGIN CHANNEL")
	testChannel()

	log.Println("BEGIN RING BUFFER")
	testRingBuffer()
}

func testChannel() {
	queue := make(chan int)
	wg := &sync.WaitGroup{}
	wg.Add(2)

	begin := time.Now()
	go func() {
		for i := 0; i < 10000; i++ {
			queue <- i
		}
		close(queue)
		wg.Done()
	}()

	go func() {
		for x := range queue {
			process(x)
		}
		wg.Done()
	}()

	wg.Wait()
	end := time.Now()

	duration := end.Sub(begin)
	log.Println(duration)
}

func testRingBuffer() {
	size := 2 * 10000 * 10000
	ringBuffer := NewRingBuffer(size)
	writer := &Writer{ringBuffer, 0}
	reader := &Reader{ringBuffer, 0}

	wg := &sync.WaitGroup{}
	wg.Add(2)
	begin := time.Now()

	go writeCycle(reader, writer, wg)
	go readCycle(reader, writer, wg)

	wg.Wait()
	end := time.Now()

	duration := end.Sub(begin)
	log.Println(duration)
}

func writeCycle(reader *Reader, writer *Writer, wg *sync.WaitGroup) {
	for i := 0; i < 10000; i++ {
		writer.Write(i)
	}

	over = true
	wg.Done()
}

func readCycle(reader *Reader, writer *Writer, wg *sync.WaitGroup) {

	for {
		if reader.Counter == writer.Counter && over {
			break
		}

		if reader.Counter == writer.Counter {
			continue
		}

		value := reader.RingBuffer.Slots[reader.Counter]
		process(value.(int))
		reader.Counter = reader.Counter + 1
	}

	wg.Done()
}

func process(value int) {
}

type RingBuffer struct {
	Slots []interface{}
}

func NewRingBuffer(size int) *RingBuffer {
	return &RingBuffer{
		Slots: make([]interface{}, size),
	}
}

type Writer struct {
	RingBuffer *RingBuffer
	Counter    int
}

func (r *Writer) Write(value int) {
	r.RingBuffer.Slots[r.Counter] = value
	r.Counter = r.Counter + 1
}

type Reader struct {
	RingBuffer *RingBuffer
	Counter    int
}
