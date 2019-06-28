package service

type Payload interface {
	Get(string) (interface{}, bool)
	Set(string, interface{})
}

type SimplePayload struct {
	payload map[string]interface{}
}

func (r *SimplePayload) Get(key string) (interface{}, bool) {
	value, ok := r.payload[key]
	return value, ok
}

func (r *SimplePayload) Set(key string, value interface{}) {
	r.payload[key] = value
}
