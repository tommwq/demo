package main

import (
	"flag"
	"fmt"
	"log"
	"net"

	"golang.org/x/net/ipv4"
)

func main() {

	var (
		interfaceName string
		port          int
		group         string
		destPort      int
		message       string
	)

	flag.StringVar(&interfaceName, "interface", "", "interface name")
	flag.StringVar(&message, "message", "", "interface name")
	flag.IntVar(&port, "port", 0, "interface name")
	flag.StringVar(&group, "group", "", "interface name")
	flag.IntVar(&destPort, "dest-port", 0, "interface name")
	flag.Parse()

	itf, err := net.InterfaceByName(interfaceName)
	if err != nil {
		log.Fatal(err)
	}

	groupIP := net.ParseIP(group)
	underline, err := net.ListenPacket("udp4", fmt.Sprintf(":%v", port))
	if err != nil {
		log.Fatal(err)
	}
	defer underline.Close()

	conn := ipv4.NewPacketConn(underline)
	err = conn.JoinGroup(itf, &net.UDPAddr{IP: groupIP})
	if err != nil {
		log.Fatal(err)
	}

	err = conn.SetControlMessage(ipv4.FlagDst, true)
	if err != nil {
		log.Println(err)
	}

	conn.SetTOS(0x0)
	conn.SetTTL(16)
	dst := &net.UDPAddr{IP: groupIP, Port: destPort}
	//if err := conn.SetMulticastInterface(itf); err != nil {
	//	log.Println(err)
	//}
	conn.SetMulticastTTL(2)
	data := []byte(message)
	if _, err := conn.WriteTo(data, nil, dst); err != nil {
		log.Fatal(err)
	}
}
