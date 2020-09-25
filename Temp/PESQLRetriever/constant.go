package main

const (
	IMAGE_FILE_MACHINE_UNKNOWN   = 0x0
	IMAGE_FILE_MACHINE_AM33      = 0x1d3
	IMAGE_FILE_MACHINE_AMD64     = 0x8664
	IMAGE_FILE_MACHINE_ARM       = 0x1c0
	IMAGE_FILE_MACHINE_ARM64     = 0xaa64
	IMAGE_FILE_MACHINE_ARMNT     = 0x1c4
	IMAGE_FILE_MACHINE_EBC       = 0xebc
	IMAGE_FILE_MACHINE_I386      = 0x14c
	IMAGE_FILE_MACHINE_IA64      = 0x200
	IMAGE_FILE_MACHINE_M32R      = 0x9041
	IMAGE_FILE_MACHINE_MIPS16    = 0x266
	IMAGE_FILE_MACHINE_MIPSFPU   = 0x366
	IMAGE_FILE_MACHINE_MIPSFPU16 = 0x466
	IMAGE_FILE_MACHINE_POWERPC   = 0x1f0
	IMAGE_FILE_MACHINE_POWERPCFP = 0x1f1
	IMAGE_FILE_MACHINE_R4000     = 0x166
	IMAGE_FILE_MACHINE_RISCV32   = 0x5032
	IMAGE_FILE_MACHINE_RISCV64   = 0x5064
	IMAGE_FILE_MACHINE_RISCV128  = 0x5128
	IMAGE_FILE_MACHINE_SH3       = 0x1a2
	IMAGE_FILE_MACHINE_SH3DSP    = 0x1a3
	IMAGE_FILE_MACHINE_SH4       = 0x1a6
	IMAGE_FILE_MACHINE_SH5       = 0x1a8
	IMAGE_FILE_MACHINE_THUMB     = 0x1c2
	IMAGE_FILE_MACHINE_WCEMIPSV2 = 0x169
)

const (
	DOS    = 0x5a4d     // MZ
	OS2    = 0x454e     // NE
	OS2_LE = 0x454c     // LE
	NT     = 0x00004550 // PE00
)

const (
	IMAGE_DIRECTORY_ENTRY_EXPORT = iota
	IMAGE_DIRECTORY_ENTRY_IMPORT
	IMAGE_DIRECTORY_ENTRY_RESOURCE
	IMAGE_DIRECTORY_ENTRY_EXCEPTION
	IMAGE_DIRECTORY_ENTRY_SECURITY
	IMAGE_DIRECTORY_ENTRY_BASERELOC
	IMAGE_DIRECTORY_ENTRY_DEBUG
	IMAGE_DIRECTORY_ENTRY_ARCHITECTURE
	IMAGE_DIRECTORY_ENTRY_GLOBALPTR
	IMAGE_DIRECTORY_ENTRY_TLS
	IMAGE_DIRECTORY_ENTRY_LOAD_CONFIG
	IMAGE_DIRECTORY_ENTRY_BOUND_IMPORT
	IMAGE_DIRECTORY_ENTRY_IAT
	IMAGE_DIRECTORY_ENTRY_DELAY_IMPORT
	IMAGE_DIRECTORY_ENTRY_COM_DESCRIPTOR
)

const (
	IMAGE_SUBSYSTEM_UNKNOWN                  = 0
	IMAGE_SUBSYSTEM_NATIVE                   = 1
	IMAGE_SUBSYSTEM_WINDOWS_GUI              = 2
	IMAGE_SUBSYSTEM_WINDOWS_CUI              = 3
	IMAGE_SUBSYSTEM_OS2_CUI                  = 5
	IMAGE_SUBSYSTEM_POSIX_CUI                = 7
	IMAGE_SUBSYSTEM_NATIVE_WINDOWS           = 8
	IMAGE_SUBSYSTEM_WINDOWS_CE_GUI           = 9
	IMAGE_SUBSYSTEM_EFI_APPLICATION          = 10
	IMAGE_SUBSYSTEM_EFI_BOOT_SERVICE_DRIVER  = 11
	IMAGE_SUBSYSTEM_EFI_RUNTIME_DRIVER       = 12
	IMAGE_SUBSYSTEM_EFI_ROM                  = 13
	IMAGE_SUBSYSTEM_XBOX                     = 14
	IMAGE_SUBSYSTEM_WINDOWS_BOOT_APPLICATION = 16
)

const (
	PE32      = 0x10b
	PE32_PLUS = 0x20b
)