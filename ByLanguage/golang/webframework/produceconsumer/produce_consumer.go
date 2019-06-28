package produceconsumer

import (
	"tq/webframework/backgroundrunner"
)

type ProduceConsumer interface {
	Producer
	Consumer
}

type BackgroundProduceConsumer interface {
	BackgroundProducer
	Consumer
}

type ProducerConsumerConnector interface {
	SetProducer(Producer)
	SetConsumer(Consumer)
}

// TODO MultiConsumerJointer
// TODO MultiProducterJointer

type BackgroundProducerConsumerConnector interface {
	ProducerConsumerConnector
	backgroundrunner.BackgroundRunner
}
