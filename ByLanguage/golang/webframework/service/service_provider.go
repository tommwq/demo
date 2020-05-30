package service

// ServiceProvider 服务提供者
type ServiceProvider interface {
	Serve(interface{}) interface{}
}

// ServiceContext 服务上下文
type ServiceContext interface {
	// ID 获取上下文编号。
	ID() int64
	// State 获取上下文状态。
	State() interface{}
	// Update 更新上下文状态。
	Update(interface{})
}

// SimpleServiceContext 简单服务上下文
type SimpleServiceContext struct {
	id    int64
	state interface{}
}

// NewSimpleServiceContext 建立简单服务上下文对象。
func NewSimpleServiceContext(id int64) *SimpleServiceContext {
	return &SimpleServiceContext{id: id}
}

func (r *SimpleServiceContext) ID() int64 {
	return r.id
}

func (r *SimpleServiceContext) State() interface{} {
	return r.state
}

func (r *SimpleServiceContext) Update(state interface{}) {
	r.state = state
}
