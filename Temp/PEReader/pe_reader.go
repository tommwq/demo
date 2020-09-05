package main

import (
	"bytes"
	"encoding/binary"
	"io"
	"io/ioutil"
	"log"
	"strings"
)

func main() {
	fileName := "C:/Users/WangQian/Game/duizhanpingtai/ClientInt/bin64/platform/Osg3D/sqlite3.dll"
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

	optionalHeader := ImageOptionalHeader64{}
	err = binary.Read(reader, binary.LittleEndian, &optionalHeader)
	if err != nil {
		log.Fatal(err)
	}

	keywords := []string{
		"select",
		"insert",
		"update",
		"delete",
	}

	sectionHeader := ImageSectionHeader{}
	for i := 0; i < 10; i++ {
		err = binary.Read(reader, binary.LittleEndian, &sectionHeader)
		if err != nil {
			log.Fatal(err)
		}

		sectionName := strings.Trim(string(sectionHeader.Name[:]), "\000")
		if sectionName == "" {
			break
		} else if sectionName == ".rdata" {
			log.Println(sectionHeader.PointerToRawData, sectionHeader.SizeOfRawData, len(data))
			sectionData := data[sectionHeader.PointerToRawData : sectionHeader.PointerToRawData+sectionHeader.SizeOfRawData]
			for _, block := range bytes.Split(sectionData, []byte("\000")) {
				s := strings.Trim(string(block), "\000")
				for _, keyword := range keywords {
					if strings.Contains(s, keyword) || strings.Contains(s, strings.ToUpper(keyword)) {
						log.Println(s)
					}
				}
			}
		}

		log.Println(sectionName)
	}

	// symbolTable := imageFileHeader.PointerToSymbolTable
	// symbol := ImageSymbol{}
	// log.Println(symbolTable)
	// reader = bytes.NewReader(data[symbolTable:len(data)])
	// for i := 0; i < int(imageFileHeader.NumberOfSymbols); i++ {
	// 	err = binary.Read(reader, binary.LittleEndian, &symbol)
	// 	if err != nil {
	// 		log.Fatal(err)
	// 	}
	// 	log.Println(symbol)
	// }

	dirEntry := optionalHeader.DataDirectory[IMAGE_DIRECTORY_ENTRY_EXPORT]
	log.Println(dirEntry.VirtualAddress, dirEntry.Size)

	reader = bytes.NewReader(data[dirEntry.VirtualAddress : dirEntry.VirtualAddress+dirEntry.Size])
	dir := ImageExportDirectory{}
	err = binary.Read(reader, binary.LittleEndian, &dir)
	log.Printf("%v %x %x\n", err, dir.NumberOfFunctions, dir.NumberOfNames)
}
