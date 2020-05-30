        .equ SYSCALL_EXIT, 1
        .equ SYSCALL_WRITE, 4
        .equ SYSCALL_CLOSE, 6
        .equ STDOUT, 1
        .equ STDERR, 2
        .equ SYSCALL_INTERRUPT, 0x80

        .section .data

message:
        .ascii "Hello, world!\r\n\0"

        .section .text
        .globl _start
_start:
        movl $SYSCALL_WRITE, %eax
        movl $STDERR, %ebx
        movl $message, %ecx
        movl $15, %edx

        int $SYSCALL_INTERRUPT

        movl %eax, %ebx

        movl $SYSCALL_CLOSE, %eax
        int $SYSCALL_INTERRUPT

        movl $SYSCALL_EXIT, %eax
        int $SYSCALL_INTERRUPT
