
        bits 32

        mov eax, 0x0a0000
        mov ebx, 0x0b0000
loop:
        cmp eax, ebx
        jz finish
        mov byte [eax], 0x0f
        inc eax
        jmp loop
        
finish:
        nop
        nop
        jmp finish
