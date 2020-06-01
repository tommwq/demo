    %include "common.asm"
    
    bits 16

    org BOOT_LOADER_ADDRESS

    xor ax, ax
    mov ds, ax
    mov ss, ax
    mov es, ax
    mov sp, BOOT_STACK

    jmp Boot

    ;; ClearScreen16()
ClearScreen16:
    mov ah, 0x07
    mov al, 0x00
    mov bh, 0x07
    mov cx, 0x00
    mov dh, 0x18
    mov dl, 0x4f
    int 0x10

    mov ah, 0x02
    mov bh, 0x01
    mov dh, 0x00
    mov dl, 0x00
    int 0x02
    
    ret
    
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

    ;; FloppyReset16()
FloppyReset16:
    mov ah, 0
    mov dl, 0
    int 0x13
    ret

    ;; ReadFloppy16()
ReadFloppy16:
    mov ah, 2
    mov al, 1
    mov ch, 0
    mov cl, 2
    mov dh, 0
    mov dl, 0
    mov bx, KERNEL_ADDRESS
    int 0x13

    ;; Error16()
Error16:
    push message_error
    call Print16
    add sp, 2
    ret

segment_descriptor_table:
    dw 0x18
    dq BOOT_LOADER_ADDRESS + $ + 8
    
null_segment_descriptor:    
    new_segment_descriptor 0, 0, 0, 0, 0, 0, 0, 0, 0
code_segment_descriptor:    
    new_segment_descriptor 0, 0xFFFF, 7, 1, 1, 1, 0, 0, 1
data_segment_descriptor:    
    new_segment_descriptor 0, 0xFFFF, 7, 1, 1, 1, 0, 0, 1

Boot:   
    call ClearScreen16

    push message_startup
    call Print16
    add sp, 2

    call FloppyReset16
    jc BootError

    push message_reset_floppy
    call Print16
    add sp, 2

    call ReadFloppy16
    jc BootError

    push message_read_floppy
    call Print16
    add sp, 2
    
    jmp BootOver
BootError:  
    call Error16
BootOver:
    jmp KERNEL_ADDRESS

message_startup:
    db 'Start up', 0x0a, 0x0d, 0x00

message_reset_floppy:
    db 'Reset floppy', 0x0a, 0x0d, 0x00

message_read_floppy:
    db 'Read floppy', 0x0a, 0x0d, 0x00

message_error:
    db 'ERROR', 0x0a, 0x0d, 0x00
    
    times 510 - ($ - $$) db 0
    dw 0xaa55                   
    
