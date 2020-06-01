    %include "common.asm"
    
    bits 32

    org KERNEL_ADDRESS

    mov ax, 0x10
    mov ds, ax
    mov ss, ax
    mov es, ax
    mov fs, ax
    mov gs, ax
    mov esp, 0x2ffff

    jmp KernelEntry
    
    ;; Print()
Print:
    mov edi, 0xb8000
    mov byte [edi + 0], 'K'
    mov byte [edi + 2], 'E'
    mov byte [edi + 4], 'R'
    mov byte [edi + 6], 'N'
    mov byte [edi + 8], 'E'
    mov byte [edi + 10], 'L'
    mov byte [edi + 12], 0x00
    ret

KernelEntry:   
    call Print
KernelOver: 
    nop
    jmp KernelOver

