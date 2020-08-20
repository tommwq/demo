package main

import (
	"testing"
)

var (
	array = []int{
		1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
		1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
		1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
		1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
		1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
		1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
		1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
		1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
		1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
		1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
	}
)

func BenchmarkUnsafeSum(b *testing.B) {
	for i := 0; i < b.N; i++ {
		unsafeSum(array)
	}
}

func BenchmarkSum(b *testing.B) {
	for i := 0; i < b.N; i++ {
		sum(array)
	}
}

func TestUnsafeSum(t *testing.T) {
	if result := unsafeSum(array); result != 550 {
		t.Fatal("expect %v get %v", 550, result)
	}
}

func TestUnsafeSum2(t *testing.T) {
	if result := unsafeSum2(array); result != 550 {
		t.Fatal("expect %v get %v", 550, result)
	}
}

func BenchmarkUnsafeSum2(b *testing.B) {
	for i := 0; i < b.N; i++ {
		unsafeSum2(array)
	}
}

func TestUnsafeSum3(t *testing.T) {
	if result := unsafeSum3(array); result != 550 {
		t.Fatal("expect %v get %v", 550, result)
	}
}

func BenchmarkUnsafeSum3(b *testing.B) {
	for i := 0; i < b.N; i++ {
		unsafeSum3(array)
	}
}
