;;; nasm -f bin boot.asm -o boot.bin

    %include "segment.asm"
    
    bits 16

    lgdt [0x7c00 + segment_descriptor_table]
    hlt

segment_descriptor_table:
    dw 0x18
    dq 0x7c00 + $ + 8
    
null_segment_descriptor:    
    new_segment_descriptor 0, 0, 0, 0, 0, 0, 0, 0, 0
code_segment_descriptor:    
    new_segment_descriptor 0, 0, 0, 0, 0, 0, 0, 0, 0
data_segment_descriptor:    
    new_segment_descriptor 0, 0, 0, 0, 0, 0, 0, 0, 0

    times 510 - ($ - $$) db 0
    dw 0xaa55
