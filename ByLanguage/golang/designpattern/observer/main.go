package main

type Observer interface {
	Update()
}

type Subject interface {
	Attach(Observer)
	Detach(Observer)
	Notify()
	GetState() string
}

type ConcreteObserver struct {
	subject Subject
}

func (r *ConcreteObserver) Update() {
	println(r.subject.GetState())
}

type ConcreteSubject struct {
	state     string
	observers []Observer
}

func NewConcreteSubject() *ConcreteSubject {
	return &ConcreteSubject{
		observers: make([]Observer, 0),
	}
}

func (r *ConcreteSubject) GetState() string {
	return r.state
}

func (r *ConcreteSubject) SetState(state string) {
	r.state = state
}

func (r *ConcreteSubject) Attach(o Observer) {
	r.observers = append(r.observers, o)
}

func (r *ConcreteSubject) Detach(o Observer) {
	index := -1
	for i, ob := range r.observers {
		if o == ob {
			index = i
			break
		}
	}

	if index != -1 {
		r.observers = append(r.observers[:index], r.observers[index:]...)
	}
}

func (r *ConcreteSubject) Notify() {
	for _, o := range r.observers {
		o.Update()
	}
}

func main() {
	subject := NewConcreteSubject()
	observer1 := &ConcreteObserver{subject}
	subject.Attach(observer1)
	observer2 := &ConcreteObserver{subject}
	subject.Attach(observer2)
	observer3 := &ConcreteObserver{subject}
	subject.Attach(observer3)
	observer4 := &ConcreteObserver{subject}
	subject.Attach(observer4)

	subject.SetState("1")
	subject.Notify()

	subject.SetState("12")
	subject.Notify()
}
