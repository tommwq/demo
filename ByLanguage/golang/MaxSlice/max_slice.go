// 测试可分配的最大slice的大小。
// tommwq
// 2017年06月10日
package main

import (
	"flag"
	"fmt"
	"log"
	"os"
	"os/exec"
)

var (
	debug bool
)

func main() {
	log.SetFlags(log.LstdFlags | log.Lshortfile)

	var (
		size  int
		alloc bool
	)

	flag.BoolVar(&debug, "debug", false, "")
	flag.BoolVar(&alloc, "alloc", false, "")
	flag.IntVar(&size, "size", 0, "")
	flag.Parse()

	if alloc {
		allocSlice(size)
	} else {
		detectMaxSliceSize()
	}
}

func allocSlice(size int) {
	arr := make([]interface{}, size)
	_ = arr
}

func middle(high, low uint64) uint64 {
	return uint64(float64(high-low)*0.618) + low
}

func detectMaxSliceSize() {
	high := uint64(1 * 10000 * 10000)
	low := uint64(1)
	count := 0

	for high-low >= 1 {
		count++
		if debug {
			log.Println(count, high, low, high-low)
		}

		err := testSliceSize(high)
		if err == nil {
			low = high
			high *= 2
		} else {
			high = middle(high, low)
		}
	}

	fmt.Println("MAX SLICE SIZE:", low)
}

func testSliceSize(size uint64) error {
	// 由于OOM无法捕获，只能使用子进程验证slice大小。
	program := os.Args[0]
	cmd := exec.Command(program,
		"-alloc",
		"-size",
		fmt.Sprintf("%v", size))

	err := cmd.Start()
	if err != nil {
		log.Fatal(err)
	}

	err = cmd.Wait()

	return err
}
