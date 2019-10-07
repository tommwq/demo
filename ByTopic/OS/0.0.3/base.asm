        bits 32
        global halt, write_byte, init_screen

        section .text

halt:                          ; void halt(void)
        hlt
        ret

write_byte:                     ; void write_byte(int address, int value)
        mov ecx, [esp+4]
        mov al, [esp+8]
        mov [ecx], al
        ret

init_screen:                    ; void init_screen(void)
        mov ah, 0x00
        mov al, 0x13
        int 0x10
        ret

