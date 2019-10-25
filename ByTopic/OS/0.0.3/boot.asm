        %include "common.asm"
        ;; 加载boot loader

        bits 16

        BOOT_ADDRESS equ 0x7c00
        BOOT_LOADER_ADDRESS equ 0xc200
        GDT_ADDRESS equ 0x8000
        SVGA_INFO_ADDRESS equ 0x9000
        STACK32_BOTTOM equ 0x2ffff
        
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

        ;; 启用A20跳线
        in al, 0x92
        or al, 0x02
        out 0x92, al

        ;; 设置GDT
        xor ax, ax
        mov ds, ax
        mov es, ax
        mov si, gdt
        mov di, GDT_ADDRESS
        mov cx, gdt_end - gdt
        inc cx
        rep movsb
        mov eax, gdt_desc
        lgdt [eax]

        ;; 设置为SVGA模式 1024x768x64K
        ;; http://www.ctyme.com/intr/rb-0275.htm
        mov ax, 0x4f02
        mov bx, 0x4114
        int 0x10

        ;; 选择显示页
        mov ax, 0x4f05
        mov bx, 0x00
        mov dx, 0x00
        int 0x10

        ;; 获取SVGA信息
        mov di, SVGA_INFO_ADDRESS
        mov ax, 0x4f01
        mov cx, 0x0114
        int 0x10
        
        ;; 设置PE标志位
        mov eax, cr0
        or eax, 0x01
        mov cr0, eax

        ;; 清理流水线
        jmp flush
        nop
        nop
flush:
        ;; 设置寄存器
        mov ax, 0x10
        mov ds, ax
        mov es, ax
        mov ss, ax
        mov fs, ax
        mov gs, ax
        mov esp, STACK32_BOTTOM
        
        jmp 0x08:BOOT_LOADER_ADDRESS
        
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

gdt:
        code_segment_descriptor 0x00000000, 0x00000000
        code_segment_descriptor 0x00000000, 0xffffffff
        data_segment_descriptor 0x00000000, 0xffffffff
gdt_end:

gdt_desc:
        dw 23
        dd GDT_ADDRESS
        
        
        times 510-($-$$) db 0x00
        dw 0xaa55

idt_desc:
        dw 0x0800
        dd 0x00
