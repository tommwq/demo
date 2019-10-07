        %include "common.asm"
        ;; 加载boot loader

        bits 16

        BOOT_ADDRESS equ 0x7c00
        BOOT_LOADER_ADDRESS equ 0xc200
        GDT_ADDRESS equ 0x8000
        
        org BOOT_ADDRESS

        ;; 初始化栈
        mov ax, 0x0000
        mov ss, ax
        mov sp, 0x7c00
        mov ds, ax
        mov es, ax

        call clear_screen

        mov si, message_loading
        call print_message

        call load_boot_loader
        jc error
        
        ;; 进入保护模式
        cli

        in al, 0x92
        or al, 0x02
        out 0x92, al

        xor di, di
        mov es, di
        mov si, predefined_gdt
        mov cx, gdt_desc - gdt
        inc cx
        rep movsb
        mov eax, gdt_desc
        lgdt [eax]

        mov bx, 0x08
        mov ds, bx
        mov es, bx

        mov eax, cr0
        or eax, 0x02
        mov cr0, eax

        jmp 0:BOOT_LOADER_ADDRESS
        
error:
        mov si, message_error
        call print_message
        jmp finish

clear_screen:
        ;; 清空屏幕，将光标设置为起始位置。
        routine16_begin
        mov ax, 0x0700
        mov bx, 0x0700
        mov cx, 0x0000
        mov dx, 0x184f
        int 0x10

        mov ax, 0x0200
        mov bx, 0x0000
        mov dx, 0x0000
        int 0x10
        routine16_end

print_message:
        ;; 打印保存在si中的字符串。
        routine16_begin
        mov ah, 0x0e
        mov bx, 0x0000
print_message_loop:     
        mov al, [si]
        or al, 0x00
        je print_message_end
        int 0x10
        add si, 0x01
        jmp print_message_loop
print_message_end:
        routine16_end

load_boot_loader:
        ;; 读取磁盘的第2到17个扇区到0x7e00
        routine16_begin
        mov ax, 0x00
        mov es, ax
        mov ah, 0x02
        mov al, 0x11
        mov bx, BOOT_LOADER_ADDRESS
        mov cx, 0x0002
        mov dx, 0x0000
        int 0x13
        routine16_end

finish:
        hlt
        jmp finish

message_error:
        db 'error', 0x0a, 0x0d, 0x00
        
message_loading:
        db 'loading...', 0x0a, 0x0d, 0x00

descriptor_table_address:
        descriptor_table $+6, 2

predefined_gdt:
null_desc:      
        code_segment_descriptor 0x00000000, 0x00000000
flat_desc:      
        code_segment_descriptor 0x00000000, 0xffffffff
        
        times 510-($-$$) db 0x00
        dw 0xaa55

