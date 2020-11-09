package main

// https://docs.microsoft.com/zh-cn/windows/win32/debug/pe-format?redirectedfrom=MSDN
// https://resources.infosecinstitute.com/2-malware-researchers-handbook-demystifying-pe-file/
// https://tech-zealots.com/malware-analysis/pe-portable-executable-structure-malware-analysis-part-2/
// http://csn.ul.ie/~caolan/pub/winresdump/winresdump/doc/pefile2.html
// http://www.sunshine2k.de/reversing/tuts/tut_pe.htm

// MS-DOS头
type ImageDosHeader struct {
	Magic    uint16
	CBLP     uint16
	CP       uint16
	CRLC     uint16
	CParHdr  uint16
	MinAlloc uint16
	MaxAlloc uint16
	SS       uint16
	SP       uint16
	CSum     uint16
	IP       uint16
	CS       uint16
	LFaRlc   uint16
	OvNo     uint16
	Res      [4]uint16
	OEMID    uint16
	OEMInfo  uint16
	Res2     [10]uint16
	LFaNew   uint32
}

// PE文件头
type ImageFileHeader struct {
	Signature            uint32
	Machine              uint16
	NumberOfSections     uint16
	TimeDateStamp        uint32
	PointerToSymbolTable uint32
	NumberOfSymbols      uint32
	SizeOfOptionalHeader uint16
	Characteristics      uint16
}

// PE可选头
type ImageOptionalHeader struct {
	Magic                       uint16
	MajorLinkerVersion          uint8
	MinorLinkerVersion          uint8
	SizeOfCode                  uint32
	SizeOfInitializedData       uint32
	SizeOfUninitializedData     uint32
	AddressOfEntryPoint         uint32
	BaseOfCode                  uint32
	BaseOfData                  uint32
	ImageBase                   uint32
	SectionAlignment            uint32
	FileAlignment               uint32
	MajorOperatingSystemVersion uint16
	MinorOperatingSystemVersion uint16
	MajorImageVersion           uint16
	MinorImageVersion           uint16
	MajorSubsystemVersion       uint16
	MinorSubsystemVersion       uint16
	Win32VersionValue           uint32
	SizeOfImage                 uint32
	SizeOfHeaders               uint32
	CheckSum                    uint32
	Subsystem                   uint16
	DllCharacteristics          uint16
	SizeOfStackReserve          uint32
	SizeOfStackCommit           uint32
	SizeOfHeapReserve           uint32
	SizeOfHeapCommit            uint32
	LoaderFlags                 uint32
	NumberOfRvaAndSizes         uint32
	DataDirectory               [16]ImageDataDirectory
}

type ImageDataDirectory struct {
	VirtualAddress uint32
	Size           uint32
}

type ResourceDirectory struct {
	Characteristics uint32
	Timestamp       uint32
	MajorVersion    uint16
	MinorVersion    uint16
	NamedEntries    uint16
	IdEntries       uint16
}

type ResourceDirectoryEntry struct {
	Offset   uint32
	Size     uint32
	CodePage uint32
	Reserved uint32
}

type ImageSectionHeader struct {
	Name                 [8]byte
	Misc                 uint32 // PhysicalAddress  or VirtualSize
	VirtualAddress       uint32
	SizeOfRawData        uint32
	PointerToRawData     uint32
	PointerToRelocations uint32
	PointerToLinenumbers uint32
	NumberOfRelocations  uint16
	NumberOfLinenumbers  uint16
	Characteristics      uint32
}

type ImageSymbol struct {
	Name               [8]byte
	Value              uint32
	SectionNumber      uint16
	Type               uint16
	StorageClass       uint8
	NumberOfAuxSymbols uint8
}

type ImageExportDirectory struct {
	Characteristics       uint32
	TimeDateStamp         uint32
	MajorVersion          uint16
	MinorVersion          uint16
	Name                  uint32
	Base                  uint32
	NumberOfFunctions     uint32
	NumberOfNames         uint32
	AddressOfFunctions    uint32
	AddressOfNames        uint32
	AddressOfNameOrdinals uint16
}
