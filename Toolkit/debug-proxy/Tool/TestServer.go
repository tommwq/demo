//
// 测试DebugProxy使用的服务器。
// 接收字符串s作为请求，返回s+" ack"。
// 

package main

import (
	"flag"
	"log"
	"net"
)

var (
	port = flag.Int("port", 1080, "port")
)

func main() {
	flag.Parse()

	addr := &net.TCPAddr{
		IP: net.ParseIP("0.0.0.0"),
		Port: *port,
	}

	server, err := net.ListenTCP("tcp", addr)
	log.Println("Start listen", addr)
	if err != nil {
		log.Fatal(err)
	}
	for {
		client, err := server.AcceptTCP()
		if err != nil {
			log.Println(err)
			continue
		}
		go serve(client)
	}
}

func serve(client *net.TCPConn) {
	buffer := make([]byte, 4096)
	for {
		size, err := client.Read(buffer)
		if err != nil {
			client.Close()
			break
		}
		input := string(buffer[0:size])
		output := input + " ack"
		size, err = client.Write([]byte(output))
		if err != nil {
			client.Close()
			break
		}
	}
}

