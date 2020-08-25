// pe_file_info.go
// 2017年04月21日

// VERSIONINFO 是使用资源文件，也就是.rsrc节。
// VERSIONINFO resource
// https://msdn.microsoft.com/en-us/library/aa381058
// https://msdn.microsoft.com/en-us/library/ms646983
// http://www.mouseos.com/windows/PE_image5.html

package main

import (
	//	"flag"
	"bytes"
	"debug/pe"
	"encoding/binary"
	"fmt"
//		"io"
	"log"
	//	"path/filepath"
)

type fileInfo struct {
	Fullname     string
	MajorVersion uint16
	MinorVersion uint16
}

var (
	file    *pe.File
	err     error
	section *pe.Section
)

func main() {
	/*
		rootPath := flag.String("path", ".", "path to find pe file")
		recurse := flag.Bool("recurse", false, "recurse search subdirectory ?")
		name := flag.String("name", "a.exe", "pe file name")
		imageVersion := flag.Bool("image-version", false, "show pe file version?")
		flag.Parse()

		matchedFiles = searchFile(*rootPath, *name, *recurse)
		for _, file := range matchedFiles {
			info := queryFileInfo(file)
			printFileInfo(info)
		}
	*/

	file, err = pe.Open("hqscanner.exe")
	if err != nil {
		log.Fatal(err)
	}
	defer file.Close()

	for _, s := range file.Sections {
		if s.Name == ".rsrc" {
			data, _ := s.Data()
			tree, err := ParseResourceDirectoryTree(data, uint32(0))
			if err != nil {
				log.Fatal(err)
			}
			PrintResourceDirectoryTree(tree)
		}
	}
}

func PrintResourceDirectoryTree(tree *ResourceDirectoryTree) {
	log.Println(tree.Node)
	for _, child := range tree.Children {
		PrintResourceDirectoryTree(child)
	}
}

func ParseResourceDirectoryTree(data []byte, offset uint32) (*ResourceDirectoryTree, error) {
	node, err := ParseResourceDirectoryNode(data)
	if err != nil {
		return nil, err
	}

	tree := &ResourceDirectoryTree{}
	tree.Node = node
	count := len(node.Entries)
	tree.Children = make([]*ResourceDirectoryTree, count)
	for i := 0; i < count; i++ {
		realOffset := node.Entries[i].RealOffset()
		child, err := ParseResourceDirectoryTree(data[realOffset:], realOffset)
		log.Println(offset, realOffset)
		if err != nil {
			return nil, err
		}
		tree.Children[i] = child
	}

	return tree, nil
}

func ParseResourceDirectoryNode(data []byte) (*ResourceDirectoryNode, error) {
	reader := bytes.NewReader(data)

	node := ResourceDirectoryNode{}
	err := binary.Read(reader, binary.LittleEndian, &node.Table)
	if err != nil {
		return nil, err
	}

	count := int(node.Table.NumberOfNameEntries + node.Table.NumberOfIDEntries)
	node.Entries = make([]ResourceDirectoryEntry, count)
	err = binary.Read(reader, binary.LittleEndian, &node.Entries)
	if err != nil {
		return nil, err
	}

	err = binary.Read(reader, binary.LittleEndian, &node.String.Length)
	if err != nil {
		return nil, err
	}

	buf := make([]byte, node.String.Length)
	reader.Read(buf)

	err = binary.Read(reader, binary.LittleEndian, &node.DataEntry)
	if err != nil {
		return nil, err
	}
	
	return &node, err
}

type RsrcSection struct {
	Root ResourceDirectoryNode
}

type ResourceDirectoryTree struct {
	Node *ResourceDirectoryNode
	Children []*ResourceDirectoryTree
}

type ResourceDirectoryNode struct {
	Table ResourceDirectoryTable
	Entries []ResourceDirectoryEntry
	String ResourceDirectoryString
	DataEntry ResourceDataEntry
}

type ResourceDirectoryTable struct {
	Characteristics     uint32
	Timestamp           uint32
	MajorVersion        uint16
	MinorVersion        uint16
	NumberOfNameEntries uint16
	NumberOfIDEntries   uint16
}

type ResourceDirectoryEntry struct {
	// NameOffset or IntegerID
	NameOffsetOrID uint32
	Offset         uint32
}

func (r ResourceDirectoryEntry) IsDataEntry() bool {
	return ((r.Offset) >> 31) == 0
}

func (r ResourceDirectoryEntry) IsSubdirectory() bool {
	return ((r.Offset) >> 31) == 1
}

func (r ResourceDirectoryEntry) String() string {
	ot := "DATA ENTRY"
	if r.IsSubdirectory() {
		ot = "SUBDIRECTORY"
	}
	return fmt.Sprintf(`
NameOffsetOrID: %d
Offset: %d
OffsetType: %s`,
		r.NameOffsetOrID,
		r.RealOffset(),
		ot)
}

func (r ResourceDirectoryEntry) RealOffset() uint32 {
	return r.Offset & 0x7fffffff
}

type ResourceDirectoryString struct {
	Length uint16
	String string
}

type ResourceDataEntry struct {
	DataRVA  uint32
	Size     uint32
	Codepage uint32
	Reserved uint32
}

func (r ResourceDataEntry) String() string {
	return fmt.Sprintf(`
Data RVA: %d
Size: %d
Codepage: %d
`,
		r.DataRVA,
		r.Size,
		r.Codepage)
}

const (
	IMAGE_DIRECTORY_ENTRY_EXPORT         = 0  // Export Directory
	IMAGE_DIRECTORY_ENTRY_IMPORT         = 1  // Import Directory
	IMAGE_DIRECTORY_ENTRY_RESOURCE       = 2  // Resource Directory
	IMAGE_DIRECTORY_ENTRY_EXCEPTION      = 3  // Exception Directory
	IMAGE_DIRECTORY_ENTRY_SECURITY       = 4  // Security Directory
	IMAGE_DIRECTORY_ENTRY_BASERELOC      = 5  // Base Relocation Table
	IMAGE_DIRECTORY_ENTRY_DEBUG          = 6  // Debug Directory
	IMAGE_DIRECTORY_ENTRY_COPYRIGHT      = 7  // (X86 usage)
	IMAGE_DIRECTORY_ENTRY_ARCHITECTURE   = 7  // Architecture Specific Data
	IMAGE_DIRECTORY_ENTRY_GLOBALPTR      = 8  // RVA of GP
	IMAGE_DIRECTORY_ENTRY_TLS            = 9  // TLS Directory
	IMAGE_DIRECTORY_ENTRY_LOAD_CONFIG    = 10 // Load Configuration Directory
	IMAGE_DIRECTORY_ENTRY_BOUND_IMPORT   = 11 // Bound Import Directory in headers
	IMAGE_DIRECTORY_ENTRY_IAT            = 12 // Import Address Table
	IMAGE_DIRECTORY_ENTRY_DELAY_IMPORT   = 13 // Delay Load Import Descriptors
	IMAGE_DIRECTORY_ENTRY_COM_DESCRIPTOR = 14 // COM Runtime descriptor
)
