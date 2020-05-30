package main

func main() {
	var car Car
	car = MakeCar("BMW")
	car.Beep()
	car = MakeCar("dd")
	car.Beep()
}

type Car interface {
	Beep()
}

type Benzi struct{}

func (r *Benzi) Beep() { println("BENZI") }

type BMW struct{}

func (r *BMW) Beep() { println("BMW") }

func MakeCar(car string) Car {
	switch car {
	case "BMW":
		return &BMW{}
	default:
		return &Benzi{}
	}
}
