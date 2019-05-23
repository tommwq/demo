	;;---------------------保护模式主引导扇区程序---------------------

	mov ax, 0x00 
    mov ss, ax
	mov sp, 0x7c00
	    
	mov ax, [cs:gdt_base+0x7c00]
    mov dx, [cs:gdt_base+0x7c00+0x02]
    mov bx, 0x10
    div bx

    mov ds, ax                        ;;得到base基地址，让ds指向这个地址
    mov bx, dx                        ;;得到偏移地址

	;;---------------------安装描述符---------------------
	    ;;描述符0

	mov dword [ebx+0x00], 0x00        ;;第一个描述符必须是0
    mov dword [ebx+0x04], 0x00        
	    
	    ;;描述符1
	mov dword [ebx+0x08], 0x7c0001FF
	mov dword [ebx+0x0c], 0x00409800    ;;基地址0x00007c00, 段界限0x001FF, 粒度是字节，
	                                    ;;长度是512字节，在内存中的32位段，特权级为0，只能执行的代码段
	    ;;描述符2
	mov dword [ebx+0x10], 0x8000FFFF    
	mov dword [ebx+0x14], 0x0040920B ;;基地址0x000B8000, 段界限0x0FFFF, 粒度是字节，
	                                    ;;长度是64KB，在内存中的32位段，特权级为0，可以读写的向上拓展的数据段
	    ;;描述符3                                
	mov dword [ebx+0x18], 0x00007A00    
	mov dword [ebx+0x2c], 0x00409600 ;;基地址0x00000000, 段界限0x07A00, 粒度是字节，
	                                    ;;在内存中的32位段，特权级为0，可以读写的向下拓展的栈段
	                                    
	mov word [cs:gdt_size+0x7c00], 31;;写入GDT段界限，4个描述符是32个字节，所以界限就是31
	lgdt [cs:gdt_size+0x7c00]        ;;load gdt
	    
	mov dx, 0x92                        ;;南桥ICH芯片内的端口0x92
	in al, dx
	or al, 0x02
	out dx, al                        ;;打开A20
	    
	cli                                ;;关闭中断
	    
	mov eax, cr0
	or eax, 0x01
	mov cr0, eax                        ;;设置PE位，处理器进入保护模式
	    
	;;保护模式
	jmp 0x0008:flush                ;;现在是在16位保护模式下，0x0008依然是段的选择子，而flush则是偏移地址

    [bits 32]
flush:    

    mov cx, 0x0010                    
	mov ds, cx
	    
	;;以下在屏幕上显示"Protect mode OK." 

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

	gdt_size    dw 0
    gdt_base    dd 0x00007e00        ;;GDT的物理地址, 主引导扇区是512个字节，这个地址刚好在主引导扇区之后 
	                             
	times 510-($-$$) db 0
    db 0x55, 0xaa
