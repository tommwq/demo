package main

import (
	"log"
)

type TrackingService struct{}

func (r TrackingService) RecordArrival(ship *Ship, port string) {
	ship.Location = port
	log.Printf("ship %s arrivals %s", ship.Name, port)
}

func (r TrackingService) RecordDeparture(ship *Ship, port string) {
	log.Printf("ship %s departures %s", ship.Name, port)
}
