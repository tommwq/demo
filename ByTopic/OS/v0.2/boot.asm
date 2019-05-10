;;; nasm -f bin boot.asm -o boot.bin

    bits 16
    fds

    mov ax, 0x07c0
    mov ds, ax
    mov ax, 0x07e0
    mov ss, ax
    mov sp, 0x2000

    call clear_screen

    push 0x0000
    call move_cursor
    add sp, 2
    
    push msg
    call print
    add sp, 2

    cli
    hlt

clear_screen:
    push bp
    mov bp, sp
    pusha

    mov ah, 0x07
    mov al, 0x00
    mov bh, 0x07
    mov cx, 0x00
    mov dh, 0x18
    mov dl, 0x4f
    int 0x10

    popa
    mov sp, bp
    pop bp
    ret

move_cursor:
    push bp
    mov bp, sp
    pusha

    mov dx, [bp+4]
    mov ah, 0x02
    mov bh, 0x00
    int 0x10

    popa
    mov sp, bp
    pop bp
    ret

print:
    push bp
    mov bp, sp
    pusha

    mov si, [bp+4]
    mov bh, 0x00
    mov bl, 0x00
    mov ah, 0x0e

    .char:
    mov al, [si]
    add si, 1
    or al, 0
    je .return
    int 0x10
    jmp .char
    .return:
    popa
    mov sp, bp
    pop bp
    ret

msg:     db "hello"
    
    times 510 - ($ - $$) db 0
    dw 0xaa55
