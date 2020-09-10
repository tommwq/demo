package main

import (
	"bytes"
	"encoding/binary"
	"io"
	"io/ioutil"
	"log"
	"os"
	"strings"
	"unicode"
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

	isPE32Plus := optionalHeader.Magic == PE32_PLUS
	if isPE32Plus {
		log.Println("64-bit")
	} else {
		log.Println("32-bit")
	}

	log.Printf("SECTION COUNT: %v\n", imageFileHeader.NumberOfSections)

	sectionHeader := ImageSectionHeader{}
	for i := 0; i < int(imageFileHeader.NumberOfSections); i++ {
		err = binary.Read(reader, binary.LittleEndian, &sectionHeader)
		if err != nil {
			log.Fatal(err)
		}

		sectionName := strings.Trim(string(sectionHeader.Name[:]), "\000")
		log.Printf("SECTION %v\n", sectionName)

		if sectionName != ".rdata" {
			continue
		}

		log.Println(sectionHeader.PointerToRawData, sectionHeader.SizeOfRawData, len(data))
		sectionData := data[sectionHeader.PointerToRawData : sectionHeader.PointerToRawData+sectionHeader.SizeOfRawData]
		for _, block := range bytes.Split(sectionData, []byte("\000")) {
			str := strings.Trim(string(block), "\000")
			if len(str) > 0 && StringIsPrint(str) {
				log.Println(str)
			}
		}
	}
}
