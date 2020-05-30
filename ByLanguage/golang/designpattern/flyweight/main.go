package main

func main() {
	manager := NewFlyweightManager()
	manager.Get("foo").Do("foo")
	manager.Get("foo").Do("bar")
	manager.Get("bar").Do("foo")
	manager.Get("bar").Do("bar")
	manager.Get("foo").Do("bar")
}

type Flyweight interface {
	Do(action string)
}

type FlyweightManager struct {
	flyweights map[string]Flyweight
}

func NewFlyweightManager() *FlyweightManager {
	return &FlyweightManager{
		flyweights: make(map[string]Flyweight),
	}
}

func (r *FlyweightManager) Get(tag string) Flyweight {
	f, ok := r.flyweights[tag]
	if !ok {
		f = &MyFlyweight{Tag: tag}
		r.flyweights[tag] = f
	}
	return f
}

type MyFlyweight struct {
	Tag string
}

func (r *MyFlyweight) Do(action string) {
	println(r, r.Tag, action)
}
