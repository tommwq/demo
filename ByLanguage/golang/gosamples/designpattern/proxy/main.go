package main

type Subject interface {
	Request()
}

type Proxy struct {
	underlying Subject
}

func NewProxy(subject Subject) *Proxy {
	return &Proxy{
		underlying: subject,
	}
}

func (r *Proxy) Request() {
	r.underlying.Request()
}

type RealSubject struct{}

func (r *RealSubject) Request() {
	println("RealSubject.Request")
}

func main() {
	subject := &RealSubject{}
	proxy := NewProxy(subject)
	proxy.Request()
}
