
    ;; 段描述符
    struc segment_descriptor
    	.segment_limit1:    resw 1
    	.base_address1:     resw 1
    	.base_address2:     resb 1
    	.type_and_flag1:             resb 1
    	.limit_and_flag2:   resb 1
    	.base_address3:     resb 1
    endstruc

    ;; 数据段输入：基地址、段界限、类型、DPL、P、AVL、G、B。
    ;; 代码段输入：基地址、短界限、类型、DPL、P、AVL、D、G。
    ;; 系统段：基地址、段界限、类型、DPL、P、G。

    ;; 建立一个段描述符
    %macro new_segment_descriptor 9
    ;; base_address 1, segment_limit 2, type 3, DPL 4, P 5,
    ;; AVL 6, B/D 7, G 8, is_normal_segment(non-system segment) 9
    	istruc segment_descriptor
    		at segment_descriptor.segment_limit1,   dw (%2 & 0x0000FFFF)
    		at segment_descriptor.base_address1,    dw (%1 & 0x0000FFFF)
    		at segment_descriptor.base_address2,    db (%1 & 0x00FF0000)
    		at segment_descriptor.type_and_flag1,   db (%3 + (%9 << 4) + (%4 << 5) + (%5 << 7))
    		at segment_descriptor.limit_and_flag2,  db ((%2 & 0x00000F00) + (%6 << 4) + (%7 << 6) + (%8 << 7))
    		at segment_descriptor.base_address3,    db (%1 & 0xFF000000)  
    	iend
    %endmacro
    
    

    %define DATA_SEGMENT 0x07e0
    %define BOOT_LOADER_ADDRESS 0x7c00
    %define BOOT_STACK 0x2000
    %define BOOT_SECTOR_TAIL 0xaa55
    %define KERNEL_ADDRESS 0x7e00

