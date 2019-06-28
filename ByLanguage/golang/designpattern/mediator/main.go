package main

import "log"

func main() {
	postman := NewPostman()

	tom := NewPerson("Tom")
	jerry := NewPerson("Jerry")
	bob := NewPerson("Bob")

	tom.SetPostman(postman)
	jerry.SetPostman(postman)
	bob.SetPostman(postman)

	bob.Send("Tom", "Hello, Tom!")
	bob.Send("Jerry", "Hello, Jerry!")
	tom.Send("B0b", "Hi, bob!")
	jerry.Send("Bob", "Hello, there!")

	log.Println(postman)

	postman.Deliver()

}

type Mail struct {
	To      string
	From    string
	Message string
}

type Postman struct {
	mails     []*Mail
	customers map[string]*Person
}

func NewPostman() *Postman {
	return &Postman{
		mails:     make([]*Mail, 0),
		customers: make(map[string]*Person),
	}
}

func (r *Postman) Receive(mail *Mail) {
	r.mails = append(r.mails, mail)
}

func (r *Postman) AddCustomer(p *Person) {
	r.customers[p.Name] = p
}

func (r *Postman) Deliver() {
	for _, mail := range r.mails {
		p, ok := r.customers[mail.To]
		if !ok {
			log.Printf("mis-addressed mail {%v}, discard it.", mail)
			continue
		}
		p.Receive(mail)
	}
	r.mails = make([]*Mail, 0)
}

type Person struct {
	Name    string
	postman *Postman
}

func NewPerson(name string) *Person {
	return &Person{
		Name: name,
	}
}

func (r *Person) Receive(mail *Mail) {
	log.Printf("{%v} receives {%v} from {%v}\r\n", r.Name, mail.Message, mail.From)
}

func (r *Person) SetPostman(postman *Postman) {
	r.postman = postman
	postman.AddCustomer(r)
}

func (r *Person) Send(to, message string) {
	if r.postman == nil {
		return
	}
	r.postman.Receive(&Mail{
		From:    r.Name,
		To:      to,
		Message: message,
	})
}
