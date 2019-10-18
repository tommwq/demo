
        .equ N, 4

        .section .text
        .globl _start
_start: 
        # ecx: next multiplier
        # ebx: result
        movl $1, %ebx
        movl $N, %ecx
multiply_next:  
        cmpl $1, %ecx
        jle end
        imull %ecx, %ebx
        decl %ecx
        jmp multiply_next
end:
        movl $1, %eax
        int $0x80
