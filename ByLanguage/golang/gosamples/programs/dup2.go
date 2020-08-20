package main

import (
	"bufio"
	"flag"
	"os"
)

func main() {
	var filename string
	flag.StringVar(&filename, "file", "a.txt", "")
	flag.Parse()

	file, err := os.Open(filename)
	if err != nil {
		panic(err)
	}
	defer file.Close()

	dup(file)
}

func dup(file *os.File) {
	counter := make(map[string]int)

	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		line := scanner.Text()
		counter[line]++
		if counter[line] > 1 {
			println(line)
		}
	}
}
