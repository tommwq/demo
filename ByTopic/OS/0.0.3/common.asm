        ;; 16位子程序入口
        %macro routine16_begin 0
        push bp
        mov bp, sp
        pusha
        %endmacro

        ;; 16位子程序出口
        %macro routine16_end 0
        popa
        mov sp, bp
        pop bp
        ret
        %endmacro

        ;; 代码段描述符
        ;; code_segment_descriptor BASE_ADDRESS, SEGMENT_LIMIT
        %macro code_segment_descriptor 2
        dw %2 & 0xffff
        dw %1 & 0xffff
        db (%1 >> 16) & 0xff
        db 0x9f
        db ((%2 >> 16) & 0x0f) + 0xd0
        db %1 >> 24
        %endmacro

        ;; 数据段描述符
        ;; data_segment_descriptor BASE_ADDRESS, SEGMENT_LIMIT
        %macro data_segment_descriptor 2
        dw %2 & 0xffff
        dw %1 & 0xffff
        db (%1 >> 16) & 0xff
        db 0x92
        db ((%2 >> 16) & 0x0f) + 0xcf
        db %1 >> 24
        %endmacro

        ;; 段选择子表
        ;; descriptor_table ADDRESS, LIMIT
        %macro descriptor_table 2
        db %2
        dw %1
        %endmacro

        ;; 段选择子
        ;; segment_selector INDEX,TI,RPL
        ;; TI: 0 GDT 1 LDT
        ;; RPL: 0 HIGHEST
        %macro segment_selector 3
        dw (%1 << 3) + ((%2 & 0x1) << 2) + (%3 & 0x03)
        %endmacro

        ;; 代码段描述符
        ;; CodeSegDescLow/High base_address, segment_limit
        %define CodeSegDescLow(b, l) (((b) & 0xffff) + (((l) & 0xffff) << 16))
        %define CodeSegDescHigh(b, l) (b >> 16) & 0xff + 0x9f << 8 + ((l >> 16) & 0xf) << 16 + 0xd0 << 36 + (b >> 24) << 24

        
                
