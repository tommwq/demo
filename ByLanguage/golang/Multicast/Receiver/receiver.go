package main

import (
	"flag"
	"fmt"
	"log"
	"net"

	"golang.org/x/net/ipv4"
)

func main() {

	// 参数：
	// interface 接口名
	// port 监听端口
	// group 组IP地址

	log.SetFlags(log.LstdFlags | log.Lshortfile)

	var (
		interfaceName string
		port          int
		group         string
	)

	flag.StringVar(&interfaceName, "interface", "", "interface name")
	flag.IntVar(&port, "port", 0, "interface name")
	flag.StringVar(&group, "group", "", "interface name")
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

	buffer := make([]byte, 1500)
	size, cm, src, err := conn.ReadFrom(buffer)
	if err != nil {
		log.Fatal(err)
	}

	log.Println(string(buffer[:size]))
	log.Println(src)
	_ = cm
	_ = src
}
