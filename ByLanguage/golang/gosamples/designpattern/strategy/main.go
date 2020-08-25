package main

type Vehicle interface {
	Travel()
}

type AirPlain struct{}

func (r *AirPlain) Travel() {
	println("TRAVEL BY AIR PLAIN")
}

type Train struct{}

func (r *Train) Travel() {
	println("TRAVEL BY TRAIN")
}

type Bike struct{}

func (r *Bike) Travel() {
	println("TRAVEL BY BIKE")
}

type Person struct {
	vehicle Vehicle
}

func (r *Person) ChangeVehicle(v Vehicle) {
	r.vehicle = v
}

func (r *Person) Travel() {
	if r.vehicle == nil {
		return
	}
	r.vehicle.Travel()
}

func main() {
	person := &Person{}
	person.ChangeVehicle(&AirPlain{})
	person.Travel()
	person.ChangeVehicle(&Train{})
	person.Travel()
	person.ChangeVehicle(&Bike{})
	person.Travel()
}
