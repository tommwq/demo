;;; nasm -f bin boot.asm -o boot.bin

    %include "segment.asm"
    
    bits 16
    
    mov ax, 0x00
    mov ds, ax
    mov ax, 0x07e0
    mov ss, ax
    mov sp, 0x2000

    call clear_screen
    
    ;; 加载GDT
    lgdt [0x7c00 + segment_descriptor_table]

    ;; 打开A20
    mov dx, 0x92
    in  al, dx
    or  al, 0x02
    out dx, al

    ;; 关闭中断
    cli

    ;; 打开PE
    mov eax, cr0
    or  eax, 0x01
    mov cr0, eax
    
    ;; 跳转，进入保护模式
    jmp 0x0008:protected_mode_entry
    
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

    
    bits 32
protected_mode_entry:
    mov cx, 0x0010
    mov ds, cx
    
	mov byte [0x00], 'P'  
    mov byte [0x02], 'r'
    mov byte [0x04], 'o'
    mov byte [0x06], 't'
    mov byte [0x08], 'e'
    mov byte [0x0a], 'c'
    mov byte [0x0c], 't'
    mov byte [0x0e], ' '
    mov byte [0x10], 'm'
    mov byte [0x12], 'o'
    mov byte [0x14], 'd'
    mov byte [0x16], 'e'
    mov byte [0x18], ' '
    mov byte [0x1a], 'O'
    mov byte [0x1c], 'K'

    hlt

    gdt_size dw 0
    gdt_base dd 0x00007e00
    
segment_descriptor_table:
    dw 0x18
    dq 0x7c00 + $ + 8
    
null_segment_descriptor:    
    new_segment_descriptor 0, 0, 0, 0, 0, 0, 0, 0, 0
code_segment_descriptor:    
    new_segment_descriptor 0, 0xFFFF, 7, 1, 1, 1, 0, 0, 1
data_segment_descriptor:    
    new_segment_descriptor 0, 0xFFFF, 7, 1, 1, 1, 0, 0, 1

    times 510 - ($ - $$) db 0
    dw 0xaa55
