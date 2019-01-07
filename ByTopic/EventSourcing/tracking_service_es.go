package main

import (
	"log"
)

type ShippingEvent interface {
	Process()
}

type ArrivalEvent struct {
	ship *Ship
	port string
}

func NewArrivalEvent(ship *Ship, port string) *ArrivalEvent {
	return &ArrivalEvent{ship: ship, port: port}
}

func (r *ArrivalEvent) Process() {
	r.ship.Location = r.port
	log.Printf("ship %s arrivals %s", r.ship.Name, r.port)
}

type DepartureEvent struct {
	ship *Ship
	port string
}

func (r *DepartureEvent) Process() {
	r.ship.Location = ""
	log.Printf("ship %s departures %s", r.ship.Name, r.port)
}

func NewDepartureEvent(ship *Ship, port string) *DepartureEvent {
	return &DepartureEvent{ship: ship, port: port}
}

type TrackingServiceES struct{}

func (r TrackingServiceES) RecordArrival(ship *Ship, port string) {
	NewArrivalEvent(ship, port).Process()
}

func (r TrackingServiceES) RecordDeparture(ship *Ship, port string) {
	NewDepartureEvent(ship, port).Process()
}
