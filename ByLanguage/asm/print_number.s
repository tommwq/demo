
        .section .bss
        .lcomm buffer, 16

        .section .text

        .globl _start
_start:
        push $buffer
        push $1234
        call print_number

        movl $4, %eax
        movl $1, %ebx
        movl $buffer, %ecx
        movl $16, %edx
        int $0x80

        movl %eax, %ebx
        movl $1, %eax
        int $0x80

        .type print_number,@function
print_number:
        # cdecl void print_number(int number, char *buffer)
        pushl %ebp
        movl %esp, %ebp

        movl 8(%ebp), %eax # number
        movl $10, %ebx
        movl $0, %ecx # parsed length
divide_loop:
        movl $0, %edx
        idivl %ebx
        pushl %edx
        incl %ecx
        cmpl $0, %eax
        jne divide_loop
        movl 12(%ebp), %ebx # buffer
pop_and_print:
        cmpl $0, %ecx
        je end
        popl %eax
        addl $0x30, %eax
        movl %eax, (%ebx)
        incl %ebx
        decl %ecx
        jmp pop_and_print
end:
        movl $0x0a, %eax
        movl %eax, (%ebx)
        incl %ebx
        movl $0x00, %eax
        movl %eax, (%ebx)
        movl %ebp, %esp
        popl %ebp
        ret

