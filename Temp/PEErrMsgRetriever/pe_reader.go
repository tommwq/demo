package main

import (
	"bytes"
	"encoding/binary"
	"fmt"
	"io"
	"io/ioutil"
	"log"
	"os"
	"path"
	"strings"
	"unicode"

	"golang.org/x/text/encoding/simplifiedchinese"
)

func StringIsPrint(s string) bool {
	for _, b := range []byte(s) {
		r := rune(b)
		if !unicode.IsPrint(r) {
			return false
		}
	}
	return true
}

func main() {

	if len(os.Args) < 2 {
		log.Printf("usage: %s a.dll\n", os.Args[0])
		return
	}

	fileName := os.Args[1]
	data, err := ioutil.ReadFile(fileName)
	if err != nil {
		log.Fatal(err)
	}

	reader := bytes.NewReader(data)
	imageDosHeader := ImageDosHeader{}
	err = binary.Read(reader, binary.LittleEndian, &imageDosHeader)
	if err != nil {
		log.Fatal(err)
	}

	imageFileHeader := ImageFileHeader{}
	reader.Seek(int64(imageDosHeader.LFaNew), io.SeekStart)
	err = binary.Read(reader, binary.LittleEndian, &imageFileHeader)
	if err != nil {
		log.Fatal(err)
	}

	optionalHeader := ImageOptionalHeader{}
	err = binary.Read(reader, binary.LittleEndian, &optionalHeader)
	if err != nil {
		log.Fatal(err)
	}

	decoder := simplifiedchinese.GBK.NewDecoder()

	sectionHeader := ImageSectionHeader{}
	for i := 0; i < int(imageFileHeader.NumberOfSections); i++ {
		err = binary.Read(reader, binary.LittleEndian, &sectionHeader)
		if err != nil {
			log.Fatal(err)
		}

		sectionName := strings.Trim(string(sectionHeader.Name[:]), "\000")

		if sectionName != ".rdata" {
			continue
		}

		sectionData := data[sectionHeader.PointerToRawData : sectionHeader.PointerToRawData+sectionHeader.SizeOfRawData]
		for _, block := range bytes.Split(sectionData, []byte("\000")) {
			utf8, err := decoder.Bytes(block)
			if err != nil {
				log.Println(err)
				continue
			}

			str := string(utf8)
			match := strings.ContainsAny(str, "失未没无错")
			if match {
				fmt.Printf("%v,%v\n", getLBMNumber(fileName), str)
			}
		}
	}
}

func getLBMNumber(fileName string) string {
	return strings.TrimSuffix(path.Base(strings.ReplaceAll(fileName, "\\", "/")), ".dll")
}

func matchKeyword(text string, keywords []string) bool {
	for _, keyword := range keywords {
		if strings.Index(text, keyword) != -1 {
			return true
		}
	}

	return false
}
