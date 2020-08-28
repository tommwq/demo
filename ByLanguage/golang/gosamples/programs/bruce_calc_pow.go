package main

import (
	"bytes"
	"crypto/sha256"
	"encoding/binary"
	"log"
	"reflect"
)

type BlockHeader struct {
	Nonce   uint64
	Message string
}

func (r *BlockHeader) Marshal() []byte {
	buffer := new(bytes.Buffer)
	binary.Write(buffer, binary.LittleEndian, r.Nonce)
	buffer.WriteString(r.Message)
	return buffer.Bytes()
}

func (r *BlockHeader) Hash() []byte {
	data := sha256.Sum256(r.Marshal())
	return data[:]
}

func CheckPoW(hash []byte, zeroPrefixLength int) bool {
	zeros := make([]byte, zeroPrefixLength)
	return reflect.DeepEqual(hash, zeros)
}

func main() {
	bh := &BlockHeader{0, "HELLO, WORLD!"}
	step := 0

	for {
		h := bh.Hash()
		if CheckPoW(h, 1) {
			log.Println(h)
			break
		}
		bh.Nonce++

		if bh.Nonce%10000 == 0 {
			step++
			log.Println(step * 10000)
		}
	}
}
