package main

type HomeTheater struct {
	Audio
	DVDPlayer
	Television
}

func (r *HomeTheater) Play(dvd string) {
	r.Audio.Plugin()
	r.Audio.PowerOn()

	r.Television.Plugin()
	r.Television.PowerOn()

	r.DVDPlayer.Plugin()
	r.DVDPlayer.PowerOn()
	r.DVDPlayer.ConnectTV()
	r.DVDPlayer.ConnectAudio()
	r.DVDPlayer.InsertDVD(dvd)

	r.DVDPlayer.Play()
}

type Audio struct{}

func (r *Audio) Plugin() {
	println("audio plugin")
}

func (r *Audio) PowerOn() {
	println("audio power on")
}

type DVDPlayer struct{}

func (r *DVDPlayer) Plugin() {
	println("dvd player plugin")
}
func (r *DVDPlayer) PowerOn() {
	println("dvd player power on")
}

func (r *DVDPlayer) InsertDVD(dvd string) {
	println("insert dvd", dvd)
}

func (r *DVDPlayer) Play() {
	println("dvd player play")
}

func (r *DVDPlayer) ConnectTV() {
	println("dvd player connect tv")
}

func (r *DVDPlayer) ConnectAudio() {
	println("dvd player connect audio")
}

type Television struct{}

func (r *Television) Plugin() {
	println("tv plugin")
}

func (r *Television) PowerOn() {
	println("tv power on")
}

func (r *Television) TurnToChannel() {
	println("tv turn to channel")
}

func main() {
	audio := Audio{}
	dvdPlayer := DVDPlayer{}
	television := Television{}

	println("BEFORE FACADE:")
	audio.Plugin()
	audio.PowerOn()

	television.Plugin()
	television.PowerOn()

	dvdPlayer.Plugin()
	dvdPlayer.PowerOn()
	dvdPlayer.ConnectTV()
	dvdPlayer.ConnectAudio()
	dvdPlayer.InsertDVD("The Simpsons")

	dvdPlayer.Play()

	println("AFTER FACADE:")
	homeTheater := HomeTheater{
		DVDPlayer:  dvdPlayer,
		Audio:      audio,
		Television: television,
	}
	homeTheater.Play("The Simpons")
}
