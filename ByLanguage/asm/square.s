.equ N, 6

.section .text
.globl _start
_start:
        # ebx: result
        # ecx: remain multiply times
        # eax: base
        movl $N, %eax
        movl %eax, %ebx
        imull %eax, %ebx
        movl $1, %eax
        int $0x80

