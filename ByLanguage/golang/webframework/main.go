package main

import (
	"log"
	"net/http"
)

func main() {

	log.SetFlags(log.LstdFlags | log.Lshortfile)

	h := NewHandler()

	log.Println("Starting Handler.")
	h.Start()
	log.Println("Handler started.")

	http.Handle("/", h)

	log.Println("Start HTTP Server.")
	http.ListenAndServe(":6060", nil)
}
