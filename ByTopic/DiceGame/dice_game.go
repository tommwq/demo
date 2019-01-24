package main

import (
	"math/rand"
	"log"
	"time"
)

func init() {
	rand.Seed(int64(time.Now().Nanosecond()))
}

type Client struct {}
type CommandHandler struct {}
type QueryHandler struct {}

type Game struct {
	dice1 int
	dice2 int
	gameOver bool
	playerWin bool
}

func (r *Game) getDice1() int {
	return r.dice1
}

func (r *Game) getDice2() int {
	return r.dice2
}

func (r *Game) rollDice1() {
	r.dice1 = 1 + rand.Intn(6)
}

func (r *Game) rollDice2() {
	r.dice2 = 1 + rand.Intn(6)
}

func (r *Game) check() {
	if r.dice1 == Unrolled || r.dice2 == Unrolled {
		r.gameOver = false
		return
	}

	r.gameOver = true
	r.playerWin = false
	if r.dice1 + r.dice2 == 7 {
		r.playerWin = true
	}
}

func (r *Game) isPlayerWin() bool {
	return r.playerWin
}

func (r *Game) isGameOver() bool {
	return r.gameOver
}

const (
	Unrolled = 0
)

func NewGame() *Game {
	return &Game{Unrolled, Unrolled, false, false}
}

type DiceGameService struct {
	game *Game
}

func NewDiceGameService() *DiceGameService {
	return &DiceGameService{}	
}

func (r *DiceGameService) startGame() {
	r.game = NewGame()
	log.Println("game started")
}

func (r *DiceGameService) rollDice1() {
	log.Println("roll dice1")
	r.game.rollDice1()
	number := r.game.getDice1()
	log.Println("dice1", number)
}

func (r *DiceGameService) rollDice2() {
	log.Println("roll dice2")
	r.game.rollDice2()
	number := r.game.getDice2()
	log.Println("dice2", number)
}

func (r *DiceGameService) check() {
	log.Println("check")
	r.game.check()
}

func (r *DiceGameService) getResult() int {
	log.Println("get result")
	isGameOver, isPlayerWin := r.game.isGameOver(), r.game.isPlayerWin()
	
	if !isGameOver {
		return NotOver
	}

	if isPlayerWin {
		return PlayerWin
	}

	return PlayerLose
}

type EventStore struct {}

type Command struct {
	Command int
	Parameter int
}

const (
	CommandStartGame = iota
	CommandStopGame
	CommandDice
	CommandCheck
)

const (
	QueryGameStatus = iota
)

const (
	NotOver = iota
	PlayerWin
	PlayerLose
)

const (
	EventGameStarted = iota
	EventDice
)

func main() {
	service := NewDiceGameService()
	service.startGame()
	service.rollDice1()
	service.rollDice2()
	service.check()
	result := service.getResult()

	switch result {
	case NotOver:
		log.Println("GAME NOT OVER.")
	case PlayerWin:
		log.Println("PLAYER WIN.")
	case PlayerLose:
		log.Println("PLAYER LOSE.")
	}
}
