package lab

import (
	"testing"
)

func TestCabinetGet(t *testing.T) {
	cabinet, err := NewCabinet(128)
	if err != nil {
		t.Fatal("should not be error")
	}

	element, index, err := cabinet.Get()
	if err == nil {
		t.Fatal("should not get element from empty cabinet")
	}
	cabinet.Release(index)

	cabinet.Put(1)
	element, index, err = cabinet.Get()
	if err != nil {
		t.Fatal(err)
	}
	if element.(int) != 1 {
		t.Fatal(err)
	}
	cabinet.Release(index)

	cabinet.Put(2)
	element, index, err = cabinet.Get()
	if err != nil {
		t.Fatal(err)
	}
	if element.(int) != 2 {
		t.Fatal(err)
	}
	cabinet.Release(index)
}

func TestNewCabinet(t *testing.T) {
	_, err := NewCabinet(1)
	if err != ErrInvalidCabinetSize {
		t.Fatal("wrong error", err)
	}
}

func TestCabinetFull(t *testing.T) {
	cabinet, err := NewCabinet(3)
	if err != nil {
		t.Fatal("should not be error")
	}

	cabinet.Put(1)
	_, i1, _ := cabinet.Get()
	cabinet.Put(1)
	_, i2, _ := cabinet.Get()
	cabinet.Put(1)
	_, i3, _ := cabinet.Get()
	err = cabinet.Put(1)
	if err != ErrCabinetIsFull {
		t.Fatal("wrong error", err)
	}

	cabinet.Release(i1)
	err = cabinet.Put(4)
	if err != nil {
		t.Fatal("wrong error", err)
	}

	cabinet.Release(i2)
	cabinet.Release(i3)

	x, i4, _ := cabinet.Get()
	if x.(int) != 4 {
		t.Fatal("wrong value")
	}
	cabinet.Release(i4)
}
