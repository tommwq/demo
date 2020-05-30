#include <stdio.h>

int is_any_odd_bit_set(unsigned int x) {
    unsigned int z = x & 0x55555555;

    return z || 0;
}

int main() {

    printf("%d %d\n", 0x100, is_any_odd_bit_set(0x1));
    printf("%d %d\n", 0x0, is_any_odd_bit_set(0x0));
    
    
    return 0;
}
