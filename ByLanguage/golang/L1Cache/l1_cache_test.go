package main

import (
	"testing"
)

func BenchmarkLoop1(b *testing.B) {
	for i := 0; i < b.N; i++ {
		loop1(arr, row, col)
	}
}

func BenchmarkLoop2(b *testing.B) {
	for i := 0; i < b.N; i++ {
		loop2(arr, row, col)
	}
}
