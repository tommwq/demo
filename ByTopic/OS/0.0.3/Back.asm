    %include "common.asm"
    
    bits 16

    org BOOT_LOADER_ADDRESS

    xor ax, ax
    mov ds, ax
    mov ss, ax
    mov sp, BOOT_STACK

    jmp boot

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

segment_descriptor_table:
    dw 0x18
    dq BOOT_LOADER_ADDRESS + $ + 8
    
null_segment_descriptor:    
    new_segment_descriptor 0, 0, 0, 0, 0, 0, 0, 0, 0
code_segment_descriptor:    
    new_segment_descriptor 0, 0xFFFF, 7, 1, 1, 1, 0, 0, 1
data_segment_descriptor:    
    new_segment_descriptor 0, 0xFFFF, 7, 1, 1, 1, 0, 0, 1

boot:   
    call ClearScreen16

    push message_startup
    call Print16
    add sp, 2

    lgdt [BOOT_LOADER_ADDRESS + segment_descriptor_table]

    push message_load_gdt
    call Print16
    add sp, 2

    mov dx, 0x92
    in  al, dx
    or  al, 0x02
    out dx, al

    push message_open_a20
    call Print16
    add sp, 2
    
    cli

    mov eax, cr0
    or  eax, 0x01
    mov cr0, eax
    
    jmp 0x0040:pe_entry

    bits 32

    ;; Print32(char *s)
Print32:
    mov esi, [esp+2]
    mov ah, 0x0e
    mov bh, 0x00
    mov bl, 0x01
Print32_NextChar:
    mov al, [si]
    xor al, 0x00
    jz Print32_Over
    int 0x10
    inc si
    jmp Print32_NextChar
Print32_Over:
    ret
    
pe_entry:

    push message_switch_pe
    call Print32
    add esp, 2
    
    hlt

message_startup:
    db 'Start up', 0x0a, 0x0d, 0x00
message_load_gdt:
    db 'Load GDT', 0x0a, 0x0d, 0x00
message_switch_pe:
    db 'Switch PE', 0x0a, 0x0d, 0x00
message_open_a20:
    db 'Open A20', 0x0a, 0x0d, 0x00

    times 510 - ($ - $$) db 0
    dw 0xaa55                   
    
