        bits 32
        
        global io_halt, io_cli, io_sti
        global io_in8, io_in16, io_in32
        global io_out8, io_out16, io_out32
        global io_write_byte
        global io_lidt

        section .text

io_halt:                          ; void io_halt(void)
        hlt
        ret

io_write_byte:                     ; void io_write_byte(int address, int value)
        mov ecx, [esp+4]
        mov al, [esp+8]
        mov [ecx], al
        ret

io_cli:
        cli
        ret

io_sti:
        sti
        ret

io_in8:
        mov edx, [esp+4]
        xor eax, eax
        in al, dx
        ret

io_in16:
        mov edx, [esp+4]
        xor eax, eax
        in ax, dx
        ret

io_in32:
        mov edx, [esp+4]
        in eax, dx
        ret

io_out8:
        mov edx, [esp+4]
        mov eax, [esp+8]
        out dx, al
        ret

io_out16:
        mov edx, [esp+4]
        mov eax, [esp+8]
        out dx, ax
        ret

io_out32:
        mov edx, [esp+4]
        mov eax, [esp+8]
        out dx, eax
        ret

io_lidt:
        mov eax, [esp+4]
        shl eax, 2
        add eax, [esp+8]
        lidt [eax]
        ret
