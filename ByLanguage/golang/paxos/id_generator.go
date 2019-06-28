package main

import (
	"sync"
)

type IDGenerator struct {
	lock   sync.Mutex
	nextID int
}

func (r *IDGenerator) generateID() int {
	r.lock.Lock()
	defer r.lock.Unlock()

	r.nextID++
	return r.nextID
}
