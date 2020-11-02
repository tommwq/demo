// �������ǲ���С��
int is_little_endian(void){
    unsigned int ret = 0x1;
    return *((char *)&ret);
}

// �������ǲ��Ǵ��
int is_big_endian(void){
    unsigned int ret = 0x1;
    return (*((char *)&ret) != 1);
}

// ��base��ʼ�ĵ�offsetλ����Ϊ1��
void bit_set(char *base, unsigned int offset){
    unsigned int mask[] = { 0x01, 0x02, 0x04, 0x08,
        0x10, 0x20, 0x40, 0x80 };
    char *_base;
    unsigned int _offset;

    _base = base + offset / 8;
    _offset = offset % 8;

    *_base = *_base | mask[_offset];
}

// ��base��ʼ�ĵ�offsetλ����Ϊ0��
void bit_clear(char *base, unsigned int offset){
    unsigned int mask[] = { 0x01, 0x02, 0x04, 0x08,
        0x10, 0x20, 0x40, 0x80 };
    char *_base;
    unsigned int _offset;

    _base = base + offset / 8;
    _offset = offset % 8;

    *_base = *_base & ~(mask[_offset]);
}

// ��ӡ�ֽ��е�ÿ��λ
void print_byte(unsigned char byte){
    int i;
    unsigned int mask[] = { 0x01, 0x02, 0x04, 0x08,
        0x10, 0x20, 0x40, 0x80 };

    for (i = 0; i < sizeof(mask) / sizeof(unsigned int); ++i){
        printf("%d", ((mask[i] & byte) > 0));
    }

    printf("\n");
}

