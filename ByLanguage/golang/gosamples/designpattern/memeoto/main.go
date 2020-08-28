package main

func main() {
	o := &Originator{}
	history := NewHistoryRecorder()

	o.Do()
	history.Append(o.SaveMemeoto())

	o.Do()
	history.Append(o.SaveMemeoto())

	o.Do()
	history.Append(o.SaveMemeoto())

	o.RestoreMemeoto(history.PreviousMemeoto())
	o.RestoreMemeoto(history.PreviousMemeoto())
	o.Do()
	history.Append(o.SaveMemeoto())
}

type HistoryRecorder struct {
	memotos []*Memeoto
	index   int
}

func NewHistoryRecorder() *HistoryRecorder {
	return &HistoryRecorder{
		memotos: make([]*Memeoto, 0),
		index:   -1,
	}
}

func (r *HistoryRecorder) Append(m *Memeoto) {
	if r.index < len(r.memotos)-1 {
		r.memotos = r.memotos[:r.index+1]
	}

	r.memotos = append(r.memotos, m)
	r.index = len(r.memotos) - 1
}

func (r *HistoryRecorder) PreviousMemeoto() *Memeoto {
	if r.index <= -1 {
		return nil
	}

	index := r.index
	r.index--
	return r.memotos[index]
}

func (r *HistoryRecorder) NextMemeoto() *Memeoto {
	if r.index >= len(r.memotos) {
		return nil
	}
	index := r.index
	r.index++
	return r.memotos[index]
}

type Memeoto struct {
	State string
}

type Originator struct {
	State string
}

func (r *Originator) Do() {
	r.State += "a"
	println(r.State)
}

func (r *Originator) SaveMemeoto() *Memeoto {
	return &Memeoto{
		State: r.State,
	}
}

func (r *Originator) RestoreMemeoto(m *Memeoto) {
	r.State = m.State
	println(r.State)
}
