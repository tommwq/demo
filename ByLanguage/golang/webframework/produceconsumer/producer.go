package produceconsumer

import (
	"tq/webframework/backgroundrunner"
)

// Producer 生产者
type Producer interface {
	Produce() interface{}
}

// BackgroundProducer 在后台生产的生产者
type BackgroundProducer interface {
	backgroundrunner.BackgroundRunner
	Producer
	TryProduce() (interface{}, error)
}
