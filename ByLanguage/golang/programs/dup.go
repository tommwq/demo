package main

import (
	"bufio"
	"os"
)

func main() {
	counter := make(map[string]int)

	scanner := bufio.NewScanner(os.Stdin)
	for scanner.Scan() {
		line := scanner.Text()
		counter[line]++
		if counter[line] > 1 {
			println(line)
		}
	}
}
