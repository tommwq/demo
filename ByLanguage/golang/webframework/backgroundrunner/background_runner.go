package backgroundrunner

type Status int

const (
	// BackgroundRunner对象运行状态
	Stopped Status = iota
	Started
)

// BackgroundRunner 对象会启动一个goroutine在后台执行任务
type BackgroundRunner interface {
	Start()
	Stop()
	GetStatus() Status
	IsStarted() bool
	IsStopped() bool
}
