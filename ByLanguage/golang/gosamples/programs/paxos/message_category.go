package main

type MessageCategory int

func (r MessageCategory) String() string {
	switch r {
	case Prepare:
		return "Prepare"
	case Promise:
		return "Promise"
	case Accept:
		return "Accept"
	case Accepted:
		return "Accepted"
	default:
		return ""
	}
}

const (
	Prepare = MessageCategory(iota)
	Promise
	Accept
	Accepted
)
