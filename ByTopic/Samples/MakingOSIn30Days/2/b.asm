    org 0x8000

    ;; setup stack
    mov ax,0x00
    mov ss,ax
    mov sp,0x8000
    mov ds,ax
    mov es,ax

    ;; set display mode
    mov ah, 0x00
    mov al, 0x13
    int 0x10

over:   
    hlt
    jmp over
