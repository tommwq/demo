    %include "common.asm"
    
    bits 16

    org KERNEL_ADDRESS

    jmp KernelEntry
    
    ;; Print16(char *s)
Print16:
    mov bx, sp
    add bx, 2
    mov si, word [bx]
    mov ah, 0x0e
    mov bh, 0x00
    mov bl, 0x01
Print16_NextChar:
    mov al, [si]
    xor al, 0x00
    jz Print16_Over
    int 0x10
    inc si
    jmp Print16_NextChar
Print16_Over:
    ret

KernelEntry:   
    push message_kernel
    call Print16
    add sp, 2
KernelOver: 
    hlt
    jmp KernelOver

message_kernel:
    db 'KERNEL', 0x0a, 0x0d, 0x00
