        .section .data

data:
        .long 1, 3, 2, 4, 5, 0

        .section .text
        .globl _start
_start:
        # ebx保存最大值
        # eax保存读取的值
        # ecx保存数组索引
        movl $0, %ecx
        movl data(,%ecx,4), %ebx
next_number:    
        incl %ecx
        movl data(,%ecx,4), %eax
        cmpl $0, %eax
        je end
        cmpl %ebx, %eax
        jle next_number
        movl %eax, %ebx
        jmp next_number
end:
        movl $1, %eax
        int $0x80
        
