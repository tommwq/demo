package main

import (
	"log"
	"os"
)

func main() {
	r, w, e := os.Pipe()
	if e != nil {
		log.Fatal(e)
	}

	e = r.Close()
	log.Println(e)
	e = w.Close()
	log.Println(e)
	e = w.Close()
	log.Println(e)

}
