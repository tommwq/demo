package produceconsumer

import (
	"tq/webframework/backgroundrunner"
)

// Consumer 生产者
type Consumer interface {
	Consume(interface{})
}

// BackgroundConsumer 在后台消费的消费者
type BackgroundConsumer interface {
	backgroundrunner.BackgroundRunner
	Consumer
}
