package lab

// 单写多读MVCC数据。

import (
	"errors"
	"sync/atomic"
)

var (
	ErrCabinetIsEmpty     = errors.New("cabinet is empty")
	ErrCabinetIsFull      = errors.New("cabinet is full")
	ErrInvalidCabinetSize = errors.New("invalid cabinet size")
)

const (
	invalidIndex = int32(-1)
)

type Cabinet struct {
	lastUpdateIndex int32
	blocks          []struct {
		Reference int32
		Element   interface{}
	}
	size int32
}

func NewCabinet(size int) (*Cabinet, error) {
	if size < 2 {
		return nil, ErrInvalidCabinetSize
	}

	return &Cabinet{
		lastUpdateIndex: invalidIndex,
		blocks: make([]struct {
			Reference int32
			Element   interface{}
		}, size),
		size: int32(size),
	}, nil
}

func (r *Cabinet) Get() (interface{}, int32, error) {

	index := r.lastUpdateIndex
	if index == invalidIndex {
		return nil, index, ErrCabinetIsEmpty
	}

	atomic.AddInt32(&r.blocks[index].Reference, 1)
	return r.blocks[index].Element, index, nil
}

func (r *Cabinet) Put(value interface{}) error {

	index := (r.lastUpdateIndex + int32(1)) % int32(r.size)
	if r.blocks[index].Reference > 0 {
		return ErrCabinetIsFull
	}

	r.blocks[index].Element = value
	r.lastUpdateIndex = index
	return nil
}

func (r *Cabinet) Release(index int32) {

	if index < int32(0) || index >= r.size {
		return
	}

	atomic.AddInt32(&r.blocks[index].Reference, -1)
}
