#include <stdio.h>

unsigned int lower_bit_set(int n) {
    return ~(0xffffffff << n);
}

int main() {
    printf("%x %x\n", 6, lower_bit_set(6));
    printf("%x %x\n", 17, lower_bit_set(17));
    return 0;
}
