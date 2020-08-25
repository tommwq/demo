package util

// lrarray保留有限个元素。如果lrarray满了，向其插入元素会导致最早的元素被丢弃。

type LastRecentArray struct {
	underlying []interface{}
	next int
	elementNumber int
}

func NewLastRecentArray(size int) *LastRecentArray {
	if size < 0 {
		size = 0
	}

	return &LastRecentArray{
		underlying: make([]interface{}, size, size),
		next: 0,
		elementNumber: 0,
	}
}

func (r *LastRecentArray) Push(value interface{}) {
	r.underlying[r.next] = value

	limit := len(r.underlying)

	r.next++
	if r.next >= limit {
		r.next = 0
	}

	r.elementNumber++
	if r.elementNumber > limit {
		r.elementNumber = limit
	}
}

func (r *LastRecentArray) Underlying() []interface{} {
	if r.elementNumber < len(r.underlying) {
		return r.underlying[:r.elementNumber]
	}

	return r.underlying
}
