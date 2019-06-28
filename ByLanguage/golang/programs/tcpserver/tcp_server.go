// TODO 分为3个文件，一个采用goroutine_per_connection模式，一个采用context模式，一个采用erlang模式。



package main

import (
	"flag"
	"log"
	"net"
)

var (
	listener *net.TCPListener
)

func main() {
	var mode int
	flag.IntVar(&mode, "mode", 1, "test mode")
	flag.Parse()

	address, err := net.ResolveTCPAddr("tcp", "127.0.0.1:65535")
	if err != nil {
		log.Fatal(err)
	}

	listener, err := net.ListenTCP("tcp", address)
	if err != nil {
		log.Fatal(err)
	}
	defer listener.Close()


	switch mode {
	case 1:
		runMode1()
	case 2:
		runMode2()
	case 3:
		runMode3()
	default:
		runMode1()
	}
}

func runMode1() {
	for {
		client, err := listener.AcceptTCP()
		if err != nil {
			log.Fatal(err)
		}

		buffer := make([]byte, 1024)
		size, err := client.Read(buffer)
		if err == nil {
			log.Println(size, string(buffer[:size]))
			client.Write(buffer[:size])
		} else {
			log.Println("ERROR", err)
		}

		client.Close()
	}
}

func runMode2() {
	for {
		client, err := listener.AcceptTCP()
		if err != nil {
			log.Fatal(err)
		}

		buffer := make([]byte, 1024)
		size, err := client.Read(buffer)
		if err == nil {
			log.Println(size, string(buffer[:size]))
			client.Write(buffer[:size])
		} else {
			log.Println("ERROR", err)
		}

		client.Close()
	}
}

func runMode3() {
	for {
		client, err := listener.AcceptTCP()
		if err != nil {
			log.Fatal(err)
		}

		buffer := make([]byte, 1024)
		size, err := client.Read(buffer)
		if err == nil {
			log.Println(size, string(buffer[:size]))
			client.Write(buffer[:size])
		} else {
			log.Println("ERROR", err)
		}

		client.Close()
	}
}

/*
func foo(l) {
  c := l.Accept()
  go foo(l)
  // handle
}

func bar(l) {
  for {
    c := l.Accept()
    go hanle(c)
  }
}

*/
