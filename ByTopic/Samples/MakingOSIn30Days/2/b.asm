    org 0x8000

    jmp entry
    db 0x90

entry:
    mov ax,0x00
    mov ss,ax
    mov sp,0x8000
    mov ds,ax
    mov es,ax

    ;; 显示Hello, world!
    mov al, 0
    mov si, msg

putloop:
    mov al, [si]
    add si, 1
    cmp al, 0

    je fin
    mov ah, 0x0e
    mov bx, 15
    int 0x10
    jmp putloop

    mov ah, 0x00
    mov al, 0x13
    int 0x10
    
fin:
    hlt
    jmp fin
msg:
    db 0x0a, 0x0a
    db "ok"
    db 0x0a
    db 0

