package main

type Ship struct {
	Name     string
	Location string
}

func main() {
	var service1 TrackingService
	var service2 TrackingServiceES

	ship1 := &Ship{"King Roy", ""}
	ship2 := &Ship{"Prince Trevor", ""}

	service1.RecordArrival(ship1, "Hong Kong")
	service1.RecordArrival(ship2, "Los Angeles")

	service2.RecordArrival(ship1, "Hong Kong")
	service2.RecordArrival(ship2, "Los Angeles")
}
