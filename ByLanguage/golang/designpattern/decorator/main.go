package main

type Work interface {
	Insert()
}

type SquarePeg struct{}

func (r *SquarePeg) Insert() {
	println("方形桩插入")
}

type Decorator struct {
	work   Work
	before []string
	after  []string
}

func NewDecorator(work Work) *Decorator {
	d := &Decorator{
		work: work,
		before: []string{
			"挖坑", "钉模板",
		},
		after: []string{
			"清理",
		},
	}
	return d
}

func (r *Decorator) Insert() {
	r.doBefore()
	r.work.Insert()
	r.doAfter()
}

func (r *Decorator) doBefore() {
	for _, action := range r.before {
		println(action)
	}
}

func (r *Decorator) doAfter() {
	for _, action := range r.after {
		println(action)
	}
}

func main() {
	var work Work
	work = &SquarePeg{}
	decorator := NewDecorator(work)
	decorator.Insert()
}
