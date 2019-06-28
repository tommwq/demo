package main

import (
	"log"
	"fmt"
)

const (
	North iota
	South
	East
	West
)

type MapSite interface {
	Enter()
}

type Room struct {
	roomNumber int
	sides [4]int
}

func NewRoom(roomNumber int) *Room {
	return &Room{
		roomNumber: roomNumber,
	}
}

func (r *Room) GetSide(direction int) MapSite {
	return r.sites[direction]
}

func (r *Room) SetSide(direction int, site MapSite) MapSite {
	r.sites[direction] = site
}

func (r *Room) Enter() {
	fmt.Printf("ENTER ROOM<%v>\n", r.roomNumber)
}

type Wall struct {}
func (r *Wall) Enter() {
	fmt.Println("ENTER WALL")
}

type Door struct {
	room1 *Room
	room2 *Room
	isOpen bool
}

func NewDoor(r1, r2 *Room) *Door {
	return &Door {
		room1: r1,
		room2: r2,
	}
}

func (r *Door) Enter() {
	fmt.Println("ENTER DOOR")
}

func (r *Door) OtherSideFrom(room *Room) *Room {
	switch (room) {
	case r.room1:
		return room2
	case r.room2:
		return room1
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

func (r *Maze) RoomNo(roomNumber int) *Room {
	room, _ := r.rooms[roomNumber]
	return room
}

type MazeBuilder interface {
	BuildMaze()
	BuildRoom(roomNo int)
	BuildDoor(roomFrom, roomTo int)
	GetMaze() *Maze
}

type MazeGame struct {}

func (r *MazeGame) CreateMaze(builder MazeBuilder) Maze {
	builder.BuildMaze()
	builder.BuildRoom(1)
	builder.BuildRoom(2)
	builder.BuildDoor(1, 2)

	return builder.GetMaze()
}

type StandardMazeBuilder struct {
}

func main() {
	
}
