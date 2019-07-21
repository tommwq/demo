    org 0x7c00

    ;; setup stack
    mov ax,0x00
    mov ss,ax
    mov sp,0x7c00
    mov ds,ax
    mov es,ax

    ;; scroll screen
    ;; mov ah, 0x06
    ;; mov al, 0x25
    ;; mov bh, 0x00
    ;; mov bl, 0x00
    ;; mov ch, 0x00
    ;; mov cl, 0x00
    ;; mov dh, 0x24
    ;; mov dl, 0x79
    ;; int 0x10

    ;; mov ah, 0x02
    ;; mov bh, 0x01
    ;; mov dh, 0x00
    ;; mov dl, 0x00
    ;; int 0x10

    ;; reset floppy A
    mov ah, 0x00
    mov dl, 0x00
    int 0x13

    ;; ;; read sections from floppy A
    mov ax, 0x0800
    mov es, ax
    mov bx, 0x00
    mov ah, 0x02
    mov al, 0x01
    mov ch, 0x00
    mov cl, 0x02
    mov dh, 0x00
    mov dl, 0x00
    int 0x13

    jnc 0x8000

    ;; 显示Hello, world!
    
    mov al, 0x00
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

fin:
    hlt
    jmp fin

msg:
    db 0x0a, 0x0a
    db "error"
    db 0x0a
    db 0

    times 510 - ($ - $$) db 0
    dw 0xaa55
