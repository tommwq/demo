
    %include "Common.asm"
    
    global ___chkstk_ms
    global Halt
    global Nop
    
    bits 32

___chkstk_ms:
    ret

Halt:
    hlt
    ret

Nop:
    nop
    ret
