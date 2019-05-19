
    struc mytype
    .long: resd 1
    .word: resw 1
    .byte: resb 1
    .str: resb 32
    endstruc

mystruc:
    istruc mytype
    at mytype.long, dd 123456
    at mytype.word, dw 1024
    at mytype.byte, db 'x'
    at mytype.str, db 'hello, world', 13, 10, 0
    iend

    
