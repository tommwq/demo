#include <stdio.h>

unsigned int func(unsigned int x, unsigned int y) {
    unsigned int z = y;
    *((char*) &z) = *((char*) &x);
    return z;
}

int main() {
    unsigned int x = 0x89ABCDEF;
    unsigned int y = 0x76543210;
    
    printf("%x\n", func(x, y));

    return 0;
}
