package util

type SimpleIDGenerator struct {
	sequence chan int64
}

func NewSimpleIDGenerator() *SimpleIDGenerator {
	ig := &SimpleIDGenerator{sequence: make(chan int64)}
	go func() {
		id := int64(1)
		for {
			ig.sequence <- id
			if id == 0 {
				continue
			}
			id++
		}
	}()

	return ig
}

func (r *SimpleIDGenerator) NextID() (int64, bool) {
	id := <-r.sequence
	if id == 0 {
		return 0, false
	}
	return id, true
}
