
        .section .bss
        .lcomm buffer, 16

        .section .data
LF:
        .byte 0x0a

        .section .text

        .globl _start
_start:
        movl (%esp), %eax
        pushl $buffer
        pushl %eax
        call print_number
        addl $8, %esp

        pushl $buffer
        call strlen
        addl $4, %esp
        movl %eax, %edx
        movl $4, %eax
        movl $1, %ebx
        movl $buffer, %ecx
        int $0x80

        # esi: remain null's number
        # edi: argv/env offset

        movl $2, %eax
        movl %eax, %esi
        movl $0, %eax
        movl %eax, %edi

next_arg:
        cmpl $0, %esi
        je print_arg_end

        movl %edi, %eax
        addl $4, %eax
        movl %eax, %edi

        movl %esp, %eax
        addl %edi, %eax
        movl (%eax), %ecx
        pushl %ecx
        call strlen
        add $4, %esp

        cmpl $0, %eax
        jne print_next_arg
        decl %esi

print_next_arg:
        movl %eax, %edx
        movl $1, %ebx
        movl $4, %eax
        int $0x80
        call print_lf
        jmp next_arg
print_arg_end:
        movl %eax, %ebx
        movl $1, %eax
        int $0x80

        .type print_number,@function
print_number:
        # cdecl int print_number(int number, char *buffer)
        # returns: 0: success, 1: failure.
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

strlen:
        # cdecl int strlen(char *s)
        pushl %ebp
        movl %esp, %ebp
        movl $0, %eax
        movl 8(%ebp), %ebx
strlen_next:
        cmpl $0, %ebx
        je strlen_end
        cmpb $0, (%ebx)
        je strlen_end
        incl %eax
        incl %ebx
        jmp strlen_next
strlen_end:
        movl %ebp, %esp
        popl %ebp
        ret

print_lf:
        movl $4, %eax
        movl $1, %ebx
        movl $LF, %ecx
        movl $1, %edx
        int $0x80
        ret

