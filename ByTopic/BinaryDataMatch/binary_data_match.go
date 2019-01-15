package main

import (
	"log"
	"io/ioutil"
	"flag"
)

func main() {
	var (
		filename1, filename2 string
	)

	flag.StringVar(&filename1, "1", "a.out", "filename 1")
	flag.StringVar(&filename2, "2", "b.out", "filename 2")
	flag.Parse()

	data1, err := ioutil.ReadFile(filename1)
	if err != nil {
		log.Fatal(err)
	}

	data2, err := ioutil.ReadFile(filename2)
	if err != nil {
		log.Fatal(err)
	}

	size := len(data1)
	if size < len(data2) {
		size = len(data2)
	}

	for i := 0; i < size; i++ {
		if data1[i] != data2[i] {
			log.Printf("unmatch at %d %x %x\n", i, data1[i], data2[i])
			return
		}
	}
}
