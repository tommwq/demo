package main

// 将同步调用改为异步调用的示例。

import (
	"context"
	"errors"
	"log"
	"time"
)

type Payload interface {
	Get(interface{}) (interface{}, bool)
	Set(interface{}, interface{})
}

type SimpleMapPayload struct {
	values map[interface{}]interface{}
}

func (r *SimpleMapPayload) Get(key interface{}) (interface{}, bool) {
	value, ok := r.values[key]
	return value, ok
}

func (r *SimpleMapPayload) Set(key interface{}, value interface{}) {
	r.values[key] = value
}

func NewSimpleMapPayload() *SimpleMapPayload {
	return &SimpleMapPayload{
		values: make(map[interface{}]interface{}),
	}
}

type Function func(Payload) (Payload, error)

func Invoke(fn Function, input Payload, timeout time.Duration) (Payload, error) {
	ch := make(chan interface{}, 1)
	go func() {
		if result, err := fn(input); err != nil {
			ch <- err
		} else {
			ch <- result
		}
	}()

	ctx, cancel := context.WithTimeout(context.Background(), timeout)

	select {
	case result := <-ch:
		cancel() // 避免ctx继续消耗计算资源。
		if m, ok := result.(Payload); ok {
			return m, nil
		} else {
			return nil, result.(error)
		}
	case <-ctx.Done():
		return nil, ctx.Err()
	}
}

func LongAdd(input Payload) (Payload, error) {
	time.Sleep(3 * time.Second)
	var (
		a, b int
	)

	if v, ok := input.Get("a"); !ok {
		return input, errors.New("'a' is required")
	} else if a, ok = v.(int); !ok {
		return input, errors.New("'a' must be int")
	}

	if v, ok := input.Get("b"); !ok {
		return input, errors.New("'b' is required")
	} else if b, ok = v.(int); !ok {
		return input, errors.New("'b' must be int")
	}

	c := a + b
	input.Set("c", c)

	return input, nil
}

func main() {
	var output Payload
	var err error
	var start time.Time

	input := NewSimpleMapPayload()
	input.Set("a", 1)
	input.Set("b", 2)

	start = time.Now()
	if output, err = Invoke(LongAdd, input, time.Second); err != nil {
		log.Println(output, err)
	} else {
		log.Println(output.Get("c"))
	}
	log.Printf("time: %v\n", time.Now().Sub(start))

	start = time.Now()
	if output, err = Invoke(LongAdd, input, time.Minute); err != nil {
		log.Println(output, err)
	} else {
		c, _ := output.Get("c")
		log.Println(c)
	}
	log.Printf("time: %v\n", time.Now().Sub(start))
}
