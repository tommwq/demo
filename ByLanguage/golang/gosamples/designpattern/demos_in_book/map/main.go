package main

import "fmt"

type MapSite interface {
	Enter()
}

const (
	North = iota
	South
	East
	West
)

type Room struct {
	roomNumber int
	sites [4]MapSite
}

func NewRoom(roomNumber int) *Room {
	return &Room {
		roomNumber: roomNumber,
	}
}

func (r *Room) GetSide(direction int) MapSite {
	return r.sites[direction]
}

func (r *Room) SetSide(direction int, site MapSite) {
	r.sites[direction] = site
}

func (r *Room) Enter() {
}

type Wall struct {}

func (r *Wall) Enter() {
}

type Door struct {
	room1 *Room
	room2 *Room
	isOpen bool
}

func NewDoor(room1, room2 *Room) *Door {
	return &Door {
		room1: room1,
		room2: room2,
		isOpen: false,
	}
}

func (r *Door) Enter() {
}

func (r *Door) OtherSideFrom(room *Room) *Room {
	switch (room) {
	case r.room1:
		return r.room2
	case r.room2:
		return r.room1
	default:
		return nil
	}
}

type Maze struct {
	rooms map[int]*Room
}

func NewMaze() *Maze {
	return &Maze {
		rooms: make(map[int]*Room),
	}
}

func (r *Maze) AddRoom(room *Room) {
	r.rooms[room.roomNumber] = room
}

func (r *Maze) RoomNo(roomNo int) *Room {
	room, _ := r.rooms[roomNo]
	return room
}

type MazeFactory interface {
	MakeMaze() *Maze
	MakeWall() *Wall
	MakeRoom(roomNo int) *Room
	MakeDoor(r1, r2 *Room) *Door
}

func CreateMaze_origin() *Maze {
	maze := NewMaze()
	r1 := NewRoom(1)
	r2 := NewRoom(2)
	d := NewDoor(r1, r2)

	maze.AddRoom(r1)
	maze.AddRoom(r2)

	r1.SetSide(North, &Wall{})
	r1.SetSide(East, d)
	r1.SetSide(South, &Wall{})
	r1.SetSide(West, &Wall{})

	r2.SetSide(North, &Wall{})
	r2.SetSide(East, &Wall{})
	r2.SetSide(South, &Wall{})
	r2.SetSide(West, d)

	return maze
}

func CreateMaze_abstractfactory() *Maze {

	maze := NewMaze()
	r1 := NewRoom(1)
	r2 := NewRoom(2)
	d := NewDoor(r1, r2)

	maze.AddRoom(r1)
	maze.AddRoom(r2)

	r1.SetSide(North, &Wall{})
	r1.SetSide(East, d)
	r1.SetSide(South, &Wall{})
	r1.SetSide(West, &Wall{})

	r2.SetSide(North, &Wall{})
	r2.SetSide(East, &Wall{})
	r2.SetSide(South, &Wall{})
	r2.SetSide(West, d)

	return maze
}

func main() {
	CreateMaze_origin()
	CreateMaze_abstractfactory()
}

