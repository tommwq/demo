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
	_ = isPE32Plus

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
			if len(block) > 0 && !unicode.Is(unicode.Latin, rune(block[0])) {
				continue
			}
			str := strings.Trim(string(block), "\000")
			if len(str) > 0 && StringIsPrint(str) && matchKeyword(str, []string{"update ", "select ", "insert ", "delete "}) {
				process(str)
			}
		}
	}

	for table, _ := range tables {
		if table != "" {
			fmt.Printf("%v,%v\n", getLBMNumber(fileName), table)
		}
	}
}

func getLBMNumber(fileName string) string {
	return strings.TrimSuffix(path.Base(strings.ReplaceAll(fileName, "\\", "/")), ".dll")
}

var (
	tables = make(map[string]string)
)

func adjustTableName(table string) string {
	if pos := strings.Index(table, "."); pos != -1 {
		table = table[pos+1:]
	}

	if pos := strings.Index(table, ","); pos != -1 {
		table = table[:pos]
	}

	if pos := strings.Index(table, "("); pos != -1 {
		table = table[:pos]
	}

	table = strings.TrimSpace(table)

	if table == "%s" {
		table = ""
	}

	return table
}

func process(str string) {
	blocks := strings.Split(str, " ")
	// fmt.Println(blocks)
	count := len(blocks)
	inFromSubClause := false
	i := 0
	for i < count {
		block := strings.ToLower(blocks[i])
		switch block {
		case "where":
			i = count // jump out of for loop
		case "into":
			if i+1 < count {
				table := adjustTableName(blocks[i+1])
				_, ok := tables[table]
				if !ok {
					tables[table] = table
				}
				i = i + 1
			}
		case "from":
			if i+1 < count {
				if !strings.HasPrefix(blocks[i+1], "(") {
					inFromSubClause = true
					table := adjustTableName(blocks[i+1])
					_, ok := tables[table]
					if !ok {
						tables[table] = table
					}
				}
				i = i + 1
			}
		case "join":
			if i+1 < count {
				if !strings.HasPrefix(blocks[i+1], "(") {
					table := adjustTableName(blocks[i+1])
					_, ok := tables[table]
					if !ok {
						tables[table] = table
					}
				}
				i = i + 1
			}
		}

		if inFromSubClause && i > 0 && strings.HasSuffix(blocks[i-1], ",") {
			table := adjustTableName(blocks[i])
			_, ok := tables[table]
			if !ok {
				tables[table] = table
			}
			i = i + 1
		}

		i = i + 1
	}
}

func matchKeyword(text string, keywords []string) bool {
	lower := strings.ToLower(text)
	for _, keyword := range keywords {
		if strings.Index(lower, strings.ToLower(keyword)) != -1 {
			return true
		}
	}

	return false
}
