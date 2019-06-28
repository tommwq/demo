package main

import (
	"sync"
)

func Singleton(creater func(interface{}) interface{}, param interface{}) func() interface{} {
	var once sync.Once
	var instance interface{}
	var initializer = func() {
		instance = creater(param)
	}

	return func() interface{} {
		once.Do(initializer)
		return instance
	}
}

type Pool struct {
	value int
}

func (r *Pool) Get() int {
	return r.value
}

func (r *Pool) Set(x int) {
	r.value = x
}

func main() {
	singleton := Singleton(func(param interface{}) interface{} {
		println("CREATE")
		pool := &Pool{}
		pool.Set(param.(int))
		return pool
	}, 1)

	println(singleton().(*Pool).Get())
	singleton().(*Pool).Set(2)
	println(singleton().(*Pool).Get())
	singleton().(*Pool).Set(3)
	println(singleton().(*Pool).Get())

	wg := sync.WaitGroup{}
	wg.Add(10)
	for i := 0; i < 10; i++ {
		go func() {
			for j := 0; j < 100; j++ {
				singleton().(*Pool).Get()
			}
			wg.Done()
		}()
	}

	wg.Wait()
}
