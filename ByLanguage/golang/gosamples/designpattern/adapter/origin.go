package main

type Origin struct {
}

func (r *Origin) SayHello() string {
	return "Hello, [origin]!"
}
