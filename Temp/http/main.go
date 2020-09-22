package main

import (
	"fmt"
	"io"
	"log"
	"net/http"
	"os"
	"strconv"
)

func main() {
	port := 7070
	if len(os.Args) > 1 {
		number, err := strconv.ParseInt(os.Args[1], 10, 32)
		if err != nil {
			log.Fatal(err)
		}
		port = int(number)
	}

	http.HandleFunc("/", func(writer http.ResponseWriter, _ *http.Request) {
		io.WriteString(writer, "ok")
	})
	log.Fatal(http.ListenAndServe(fmt.Sprintf(":%d", port), nil))
}
