package main

import (
	"fmt"
	"log"
	"net"
	"os"
	"strconv"
	"time"
)

func display(block []byte) string {
	result := "\n"
	size := len(block)

	column := 16
	half := column / 2
	for offset := 0; offset < size; offset++ {
		result += fmt.Sprintf("%02x ", block[offset])

		pos := offset + 1
		if pos % half == 0 {
			result += "  "
		}

		if pos % column != 0 {
			continue
		}

		result += "    "
		for i := pos - column; i >= 0 && i < pos; i++ {
			c := "."
			b := block[i]
			if strconv.IsGraphic(rune(b)) && b < 128 {
				c = fmt.Sprintf("%c", b)
			}
			result += c
		}
		result += "\n"
	}

	remain := size % column
	for i := remain; i != 0 && i < column; i++ {
		result += "   "
	}

	if remain != 0 {
		if remain < column / 2 {
			result += "  "
		}
		result += "      "
		for i := 0; i < remain; i++ {
			c := "."
			b := block[size - remain + i]
			if strconv.IsPrint(rune(b)) && b < 128 {
				c = fmt.Sprintf("%c", b)
			}
			result += c
		}
		result += "\n"
	}

	return result
}

func receive(ch chan []byte, conn *net.TCPConn, tag string) {
	for {
		piece := make([]byte, 1024)
		size, err := conn.Read(piece)
		
		if err != nil {
			log.Fatal(err)
		}
		if size == 0 {
			continue
		}
		piece = piece[0:size]

		log.Println("RECEIVE FROM", tag, size, display(piece))
		ch <- piece[0:size]
	}
}

func send(conn *net.TCPConn, ch chan []byte, tag string) {
	for {
		piece := <- ch
		log.Println("SEND TO", tag, len(piece), display(piece))
		conn.Write(piece)
	}
}

func main() {
	if len(os.Args) < 4 {
		log.Println("usage: %v ListenPort Host Port", os.Args[0])
		os.Exit(0)
	}
	listenPort, err := strconv.Atoi(os.Args[1])
	host := os.Args[2]
	port, err := strconv.Atoi(os.Args[3])

	address := net.TCPAddr{Port: listenPort}
	listener, err := net.ListenTCP("tcp4", &address)
	if err != nil {
		log.Fatal(err)
	}

	connection, err := listener.AcceptTCP()
	if err != nil {
		log.Fatal(err)
	}

	forwardConnection, err := net.DialTCP("tcp4", nil, &net.TCPAddr{IP:net.ParseIP(host), Port:port})
	if err != nil {
		log.Fatal(err)
	}

	toServer := make(chan []byte, 10)
	toClient := make(chan []byte, 10)

	go send(forwardConnection, toServer, "SERVER")
	go send(connection, toClient, "CLIENT")
	go receive(toServer, connection, "CLIENT")
	go receive(toClient, forwardConnection, "SERVER")

	time.Sleep(2 * time.Hour)
	
	log.Println(forwardConnection, connection)
}
