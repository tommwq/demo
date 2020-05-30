package produceconsumer

import (
	"tq/webframework/backgroundrunner"
)

type SimpleProductDispatcher struct {
	producer Producer
	consumer Consumer
	status   backgroundrunner.Status
}

func NewSimpleProductDispatcher() *SimpleProductDispatcher {
	return &SimpleProductDispatcher{}
}

func (r *SimpleProductDispatcher) SetProducer(producer Producer) {
	r.producer = producer
}

func (r *SimpleProductDispatcher) SetConsumer(consumer Consumer) {
	r.consumer = consumer
}

func (r *SimpleProductDispatcher) Start() {
	if r.producer == nil || r.consumer == nil {
		return
	}

	r.status = backgroundrunner.Started
	go func() {
		for !r.IsStopped() {
			r.Consume(r.Produce())
			r.consumer.Consume(r.producer.Produce())
		}
	}()
}

func (r *SimpleProductDispatcher) Produce() interface{} {
	return r.producer.Produce()
}

func (r *SimpleProductDispatcher) Consume(payload interface{}) {
	r.consumer.Consume(payload)
}

func (r *SimpleProductDispatcher) Stop() {
	r.status = backgroundrunner.Stopped
}

func (r *SimpleProductDispatcher) GetStatus() backgroundrunner.Status {
	return r.status
}

func (r *SimpleProductDispatcher) IsStarted() bool {
	return r.status == backgroundrunner.Started
}

func (r *SimpleProductDispatcher) IsStopped() bool {
	return r.status == backgroundrunner.Stopped
}
