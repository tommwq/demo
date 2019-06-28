//
// 测试DebugProxy使用的客户端。
// 和TestServer配合使用。
//

package main

import (
	"bufio"
	"flag"
	"fmt"
	"log"
	"net"
	"os"
	"strings"
)

var (
	port = flag.Int("port", 1081, "port")
)

func main() {
	flag.Parse()

	addr := &net.TCPAddr{
		IP: net.ParseIP("127.0.0.1"),
		Port: *port,
	}

	client, err := net.DialTCP("tcp", nil, addr)
	if err != nil {
		log.Fatal(err)
	}

	buffer := make([]byte, 4096)
	
	for {
		fmt.Print("> ")
		reader := bufio.NewReader(os.Stdin)
		input, _ := reader.ReadString('\n')
		input = strings.Trim(input, "\n\000\r ")

		if input == "quit" {
			break
		}
		client.Write([]byte(input))
		size, err := client.Read(buffer)
		if err != nil {
			log.Println(err)
			break
		}
		output := string(buffer[0:size])
		fmt.Println(output)
	}

	client.Close()
}
