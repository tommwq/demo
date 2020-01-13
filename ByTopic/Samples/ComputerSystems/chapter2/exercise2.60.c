#include <stdio.h>

unsigned int replace_byte(unsigned int x, int position, unsigned char b) {
    unsigned int z = x;
    char *base = (char*) &z;
    base[position] = b;
    return z;
}

int main() {

    unsigned int x = 0x12345678;
    printf("0x%x\n", replace_byte(x, 2, 0xab));
    printf("0x%x\n", replace_byte(x, 0, 0xab));
    
    return 0;
}
