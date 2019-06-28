package main

import (
	"fmt"
	"net/http"
	"sync"
)

type Counter struct {
	count int
	lock  sync.RWMutex
}

func NewCounter() *Counter {
	return &Counter{
		count: 0,
	}
}

func (r *Counter) Add() {
	r.lock.Lock()
	defer r.lock.Unlock()
	r.count++
}

func (r *Counter) Get() int {
	r.lock.RLock()
	defer r.lock.RUnlock()
	return r.count
}

func main() {

	counter := NewCounter()

	http.HandleFunc("/hello", func(w http.ResponseWriter, r *http.Request) {
		fmt.Fprint(w, "hello")
		counter.Add()
	})

	http.HandleFunc("/count", func(w http.ResponseWriter, r *http.Request) {
		fmt.Fprintf(w, "count: %v", counter.Get())
	})

	http.HandleFunc("/", func(w http.ResponseWriter, r *http.Request) {
		fmt.Fprint(w, `
<html>
  <body>
    <a href="/hello">hello</a>
    <a href="/count">count</a>
  </body>
</html>
`)
	})

	err := http.ListenAndServe(":2048", nil)
	if err != nil {
		println(err)
	}
}
