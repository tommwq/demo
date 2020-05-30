package util

type IDGenerator interface {
	NextID() (int64, bool)
}
