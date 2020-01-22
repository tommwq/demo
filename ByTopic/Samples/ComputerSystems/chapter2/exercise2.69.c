#include <stdio.h>

unsigned int rotate_left(unsigned int x, int n) {
    return (x << n) | (x >> (32 - n));
}

int main() {
    unsigned int x = 0x12345678;
    printf("%x %x\n", 4, rotate_left(x, 4));
    printf("%x %x\n", 20, rotate_left(x, 20));
    return 0;
}
