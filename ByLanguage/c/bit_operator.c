// 检查机器是不是小端
int is_little_endian(void){
    unsigned int ret = 0x1;
    return *((char *)&ret);
}

// 检查机器是不是大端
int is_big_endian(void){
    unsigned int ret = 0x1;
    return (*((char *)&ret) != 1);
}

// 将base起始的第offset位设置为1。
void bit_set(char *base, unsigned int offset){
    unsigned int mask[] = { 0x01, 0x02, 0x04, 0x08,
        0x10, 0x20, 0x40, 0x80 };
    char *_base;
    unsigned int _offset;

    _base = base + offset / 8;
    _offset = offset % 8;

    *_base = *_base | mask[_offset];
}

// 将base起始的第offset位设置为0。
void bit_clear(char *base, unsigned int offset){
    unsigned int mask[] = { 0x01, 0x02, 0x04, 0x08,
        0x10, 0x20, 0x40, 0x80 };
    char *_base;
    unsigned int _offset;

    _base = base + offset / 8;
    _offset = offset % 8;

    *_base = *_base & ~(mask[_offset]);
}

// 打印字节中的每个位
void print_byte(unsigned char byte){
    int i;
    unsigned int mask[] = { 0x01, 0x02, 0x04, 0x08,
        0x10, 0x20, 0x40, 0x80 };

    for (i = 0; i < sizeof(mask) / sizeof(unsigned int); ++i){
        printf("%d", ((mask[i] & byte) > 0));
    }

    printf("\n");
}

