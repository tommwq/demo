package main

import (
	"fmt"
)

type Context struct {
	state State
}

func NewContext(initState State) *Context {
	return &Context{
		state: initState,
	}
}

func (r *Context) State() State {
	return r.state
}

func (r *Context) Done() bool {
	if r.state == nil {
		return true
	}

	return r.state.Done()
}

func (r *Context) Update(state State) {
	r.state = state
}

func (r *Context) Request() {
	if r.state == nil {
		return
	}

	r.state.Handle(r)
}

type State interface {
	Handle(context *Context)
	Done() bool
}

type SumToTenState struct {
	Value int
	Sum   int
}

func (r *SumToTenState) Handle(context *Context) {
	newState := &SumToTenState{}
	prevState := context.State()
	if prevState != nil {
		newState.Value = r.Value + 1
		newState.Sum = prevState.(*SumToTenState).Sum + r.Value
	}

	context.Update(newState)
}

func (r *SumToTenState) Done() bool {
	return (r.Value > 10) || (r.Value <= 0)
}

func (r *SumToTenState) String() string {
	return fmt.Sprintf("{%v, %v}", r.Value, r.Sum)
}

func main() {
	state := &SumToTenState{Value: 1, Sum: 0}
	context := NewContext(state)

	for !context.Done() {
		context.Request()
	}

	state = context.State().(*SumToTenState)
	fmt.Println(state)
}
