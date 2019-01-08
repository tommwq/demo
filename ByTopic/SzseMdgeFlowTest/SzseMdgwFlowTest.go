package main

import (
	"bytes"
	"encoding/binary"
	"flag"
	"fmt"
	"io"
	"log"
	"net"
	"time"
)

const (
	// Logon 深交所MDGW消息号：登录。
	Logon = 1
	// Logout 深交所MDGW消息号：登出。
	Logout = 2
	// Heartbeat 深交所MDGW消息号：心跳。
	Heartbeat = 3
)

// Serializable 序列化接口。
type Serializable interface {
	Serialize() []byte
}

// Message 消息。
type Message struct {
	Header MessageHeader
	Body   Serializable
	Tail   MessageTail
}

// NewMessage 建立消息对象。
func NewMessage(header MessageHeader, body Serializable, tail MessageTail) *Message {
	return &Message{
		Header: header,
		Body:   body,
		Tail:   tail,
	}
}

// Serialize 序列化。
func (r *Message) Serialize() []byte {
	b := r.Body.Serialize()
	r.Header.BodyLength = uint32(len(b))
	h := r.Header.Serialize()

	buf := bytes.NewBuffer(make([]byte, 0))
	buf.Write(h)
	buf.Write(b)
	r.Tail.Checksum = CalcuateChecksum(buf.Bytes())
	t := r.Tail.Serialize()
	buf.Write(t)

	return buf.Bytes()
}

// Parse 反序列化。
func Parse(data []byte) (*Message, int, error) {
	var err error
	msg := &Message{}
	reader := bytes.NewReader(data)

	err = binary.Read(reader, binary.BigEndian, &msg.Header.MsgType)
	if err != nil {
		return nil, 0, err
	}

	err = binary.Read(reader, binary.BigEndian, &msg.Header.BodyLength)
	if err != nil {
		return nil, 0, err
	}

	mb := MessageBody{
		Data: make([]byte, msg.Header.BodyLength),
	}
	_, err = reader.Read(mb.Data)
	if err != nil {
		return nil, 0, err
	}

	msg.Body = mb
	err = binary.Read(reader, binary.BigEndian, &msg.Tail.Checksum)
	if err != nil {
		return nil, 0, err
	}

	return msg, int(msg.Header.BodyLength) + 12, nil
}

// MessageHeader 深交所MDGW消息头。
type MessageHeader struct {
	// MsgType 消息类型。
	MsgType uint32
	// BodyLength 消息体长度。
	BodyLength uint32
}

// Serialize 序列化MDGW消息头。
func (r *MessageHeader) Serialize() []byte {
	buffer := bytes.NewBuffer(make([]byte, 0))
	binary.Write(buffer, binary.BigEndian, r.MsgType)
	binary.Write(buffer, binary.BigEndian, r.BodyLength)
	return buffer.Bytes()
}

// MessageBody 深交所MDGW消息体。
type MessageBody struct {
	// Data 消息体数据。
	Data []byte
}

// Serialize 序列化MDGW消息体。
func (r MessageBody) Serialize() []byte {
	return r.Data
}

// MessageTail 深交所MDGW消息尾部。
type MessageTail struct {
	// Checksum 消息校验和。
	Checksum uint32
}

// Serialize 序列化MDGW消息尾部。
func (r *MessageTail) Serialize() []byte {
	buffer := bytes.NewBuffer(make([]byte, 0))
	binary.Write(buffer, binary.BigEndian, r.Checksum)
	return buffer.Bytes()
}

// CalcuateChecksum 计算数据校验和。
func CalcuateChecksum(b []byte) uint32 {
	var sum uint32
	for _, x := range b {
		sum += uint32(x)
	}
	return sum % 256
}

// CompID 深交所MDGW数据CompID。
type CompID [20]byte

// LogonBody 深交所MDGW登录消息体。
type LogonBody struct {
	// SenderCompID 发送方。
	SenderCompID CompID
	// TargetCompID 目标方。
	TargetCompID CompID
	// HeartBtInt 心跳频率。
	HeartBtInt int32
	// Password 密码。
	Password [16]byte
	// DefaultApplVerID MDGW版本编号。
	DefaultApplVerID [32]byte
}

// SecurityID 证券编号。
type SecurityID struct {
	Exchange   string
	SecurityID string
}

// PadStringRight 填充字符串，以满足特定长度要求。
func PadStringRight(origin string, pad rune, length int) string {
	if len(origin) >= length {
		return origin
	}
	padlen := length - len(origin)
	padded := make([]rune, padlen)
	for i := 0; i < padlen; i++ {
		padded[i] = pad
	}
	return origin + string(padded)
}

// NewLogonMessage 建立登录消息。
func NewLogonMessage(sender, target, password string, heartbeat int, version string) *Message {
	header := MessageHeader{}
	header.MsgType = Logon

	body := &LogonBody{}
	copy(body.SenderCompID[0:], []byte(PadStringRight(sender, ' ', 20)))
	copy(body.TargetCompID[0:], []byte(PadStringRight(target, ' ', 20)))
	copy(body.Password[0:], []byte(PadStringRight(password, ' ', 20)))
	copy(body.DefaultApplVerID[0:], []byte(PadStringRight(version, ' ', 32)))
	body.HeartBtInt = int32(heartbeat)

	tail := MessageTail{}
	return NewMessage(header, body, tail)
}

// Serialize 序列化。
func (r *LogonBody) Serialize() []byte {
	buffer := bytes.NewBuffer(make([]byte, 0))
	buffer.Write(r.SenderCompID[0:])
	buffer.Write(r.TargetCompID[0:])
	binary.Write(buffer, binary.BigEndian, r.HeartBtInt)
	buffer.Write(r.Password[0:])
	buffer.Write(r.DefaultApplVerID[0:])
	return buffer.Bytes()
}

// HeartbeatBody 心跳包。
type HeartbeatBody struct{}

// Serialize 序列化。
func (r *HeartbeatBody) Serialize() []byte {
	return make([]byte, 0)
}

// NewHeartbeatMessage 建立心跳包。
func NewHeartbeatMessage() *Message {
	header := MessageHeader{}
	header.MsgType = Heartbeat
	body := &HeartbeatBody{}
	tail := MessageTail{}
	return NewMessage(header, body, tail)
}

func main() {
	host := ""
	port := 0
	user := ""
	password := ""

	flag.StringVar(&host, "host", "", "mdgw host")
	flag.IntVar(&port, "port", 0, "mdgw port")
	flag.StringVar(&user, "user", "", "mdgw user")
	flag.StringVar(&password, "password", "", "mdgw password")
	flag.Parse()

	log.Println("开始连接深圳MDGW。")
	address := &net.TCPAddr{
		IP:   net.ParseIP(host),
		Port: port,
	}
	connection, err := net.DialTCP("tcp", nil, address)
	if err != nil {
		log.Fatal(err.Error())
	}
	log.Println("连接深圳MDGW完成。")
	defer connection.Close()

	log.Println("开始登录深圳MDGW。")
	heartbeatPeriod := 3
	senderCompID := "Initiator"
	targetCompID := "8888"
	applVerID := "1.02"
	logonMessage := NewLogonMessage(senderCompID, targetCompID, password, heartbeatPeriod, applVerID)
	_, err = connection.Write(logonMessage.Serialize())
	if err != nil {
		log.Fatal(err.Error())
	}
	log.Println("登录深圳MDGW完成。")

	log.Println("启动心跳例程。")
	go func() {
		heartbeat := NewHeartbeatMessage().Serialize()
		for range time.Tick(time.Duration(heartbeatPeriod) * time.Second) {
			connection.Write(heartbeat)
		}
	}()

	log.Println("开始接收数据。")
	startTime := time.Now()
	var totalSize uint64

	buffer := make([]byte, 1024*1024*16)
	for {
		size, err := connection.Read(buffer)
		if err == io.EOF {
			log.Println(err.Error())
			continue
		}

		now := time.Now()
		totalSize = totalSize + uint64(size)
		seconds := now.Sub(startTime).Seconds()
		speed := float64(totalSize) / 1024.0 / 1024.0 / seconds
		log.Printf("接收数据: %s 接收数据总量：%s 平均速率: %.2fMB/s 距启动时间: %.2f秒\n", prettyVolume(uint64(size)), prettyVolume(totalSize), speed, seconds)
	}
}

func prettyVolume(byteNumber uint64) string {
	KB := 1024.0
	MB := 1024.0 * KB
	GB := 1024.0 * MB

	size := float64(byteNumber)
	if size > GB {
		return fmt.Sprintf("%.2fGB", size/GB)
	} else if size > MB {
		return fmt.Sprintf("%.2f MB", size/MB)
	} else if size > KB {
		return fmt.Sprintf("%.2f KB", size/KB)
	} else {
		return fmt.Sprintf("%.2f bytes", size)
	}
}
