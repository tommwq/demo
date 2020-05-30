package main

import (
	"log"
	"net/http"

	"tq/webframework/produceconsumer"
	"tq/webframework/service"
	"tq/webframework/serviceprovider"
)

type Handler struct {
	httpService *service.Service
	writer      produceconsumer.Consumer
	dispatcher  produceconsumer.BackgroundProducerConsumerConnector
	// Background
}

func NewHandler() *Handler {
	r := &Handler{}
	return r
}

func (r *Handler) Start() {
	r.httpService = serviceprovider.NewSimpleHTTPService()
	log.Println("Start httpService.")
	r.httpService.Start()
	log.Println("httpService started.")

	r.writer = serviceprovider.NewSimpleResponseWriter()

	r.dispatcher = produceconsumer.NewSimpleProductDispatcher()
	r.dispatcher.SetProducer(r.httpService)
	r.dispatcher.SetConsumer(r.writer)
	log.Println("Start dispatcher.")
	r.dispatcher.Start()
	log.Println("dispatcher started.")
}

func (r *Handler) ServeHTTP(writer http.ResponseWriter, request *http.Request) {
	context := serviceprovider.NewHTTPContext(writer, request)
	log.Printf("Start %v\n", context.Request.URL.Path)

	go r.httpService.Consume(context)
	<-context.Done
	log.Printf("DONE %v\n", context.Request.URL.Path)
}
