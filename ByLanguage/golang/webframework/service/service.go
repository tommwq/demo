package service

import (
	"log"

	"tq/webframework/backgroundrunner"
)

type Service struct {
	provider ServiceProvider
	input    chan ServiceContext
	output   chan ServiceContext
	status   backgroundrunner.Status
	// BackgroundProducerConsumer
}

func NewService(provider ServiceProvider) *Service {
	return &Service{provider: provider, status: backgroundrunner.Stopped}
}

func (r *Service) GetStatus() backgroundrunner.Status {
	return r.status
}

func (r *Service) IsStarted() bool {
	return r.status == backgroundrunner.Started
}

func (r *Service) IsStopped() bool {
	return r.status == backgroundrunner.Stopped
}

func (r *Service) Start() {
	r.status = backgroundrunner.Started

	r.input = make(chan ServiceContext)
	r.output = make(chan ServiceContext)

	go func() {
		for !r.IsStopped() {
			in := <-r.input
			result := r.provider.Serve(in.State())
			out := NewSimpleServiceContext(in.ID())
			out.Update(result)
			r.output <- out
		}
	}()
}

func (r *Service) Stop() {
	r.status = backgroundrunner.Stopped
}

func (r *Service) Produce() interface{} {
	context := <-r.output
	log.Printf("Service.Produce() =>%v\n", context)
	return context.State()
}

func (r *Service) Consume(payload interface{}) {
	log.Printf("Service.Consume(%v)\n", payload)
	in := NewSimpleServiceContext(0)
	in.Update(payload)
	r.input <- in
}
