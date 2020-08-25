package main

import (
	"log"
)

type Television struct {}
func (r *Television) TurnOn() {
	log.Println("tv turned on")
}
type OldController struct {
	Television
}
func (r *OldController) TurnOn() {
	r.Television.TurnOn()
}

type Device interface {
	Invoke(string)
}

type RemoteControllerAdapter struct {
	Television
}

func (r *RemoteControllerAdapter) Invoke(command string) {
	if command == "turnon" {
		r.Television.TurnOn()
	}
}

type AirConditioner struct {
}
func (r *AirConditioner) Invoke(command string) {
	if command == "turnon" {
		log.Println("ac turned on")
	}
}

type NewController struct {
	Device
}

func (r *NewController) TurnOn() {
	command := "turnon"
	r.Device.Invoke(command)
}

func (r *NewController) Bind(dev Device) {
	r.Device = dev
}

func main() {
	tv := Television{}
	oc := OldController{tv}
	oc.TurnOn()

	adapter := &RemoteControllerAdapter{tv}
	nc := NewController{adapter}
	nc.TurnOn()

	ac := &AirConditioner{}
	nc.Bind(ac)
	nc.TurnOn()
}
