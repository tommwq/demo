#include <stdio.h>

int is_all_bits_set(unsigned int x) {
    return !(1 + x) || 0;
}

int is_all_bits_clear(unsigned int x) {
    return !~(x - 1) || 0;
}

// lsb: least significant byte
int is_all_lsb_bits_set(unsigned int x) {
    unsigned z = x | 0xffffff00;
    return !(1 + z) || 0;
}

// msb: most significant byte
int is_all_msb_bits_set(unsigned int x) {
    unsigned z = x | 0x00ffffff;
    return !(1 + z) || 0;
}


int main() {

    unsigned int x;

    x = 0xffffffff;
    printf("is_all_bits_set(0x%x) = %d\n", x, is_all_bits_set(x));
    x = 0xafffffff;
    printf("is_all_bits_set(0x%x) = %d\n", x, is_all_bits_set(x));

    x = 0x0;
    printf("is_all_bits_clear(0x%x) = %d\n", x, is_all_bits_clear(x));
    x = 0x1;
    printf("is_all_bits_clear(0x%x) = %d\n", x, is_all_bits_clear(x));

    x = 0xffffffff;
    printf("is_all_lsb_bits_set(0x%x) = %d\n", x, is_all_lsb_bits_set(x));
    x = 0xfffffffa;
    printf("is_all_lsb_bits_set(0x%x) = %d\n", x, is_all_lsb_bits_set(x));

    x = 0xffffffff;
    printf("is_all_msb_bits_set(0x%x) = %d\n", x, is_all_msb_bits_set(x));
    x = 0xafffffff;
    printf("is_all_msb_bits_set(0x%x) = %d\n", x, is_all_msb_bits_set(x));
    
    return 0;
}
