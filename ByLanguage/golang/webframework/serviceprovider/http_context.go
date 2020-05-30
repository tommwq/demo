package serviceprovider

import (
	"net/http"
)

type HTTPContext struct {
	Writer  http.ResponseWriter
	Request *http.Request
	Content []byte
	Done    chan bool
}

func NewHTTPContext(writer http.ResponseWriter, request *http.Request) *HTTPContext {
	return &HTTPContext{
		Writer:  writer,
		Request: request,
		Done:    make(chan bool),
	}
}
