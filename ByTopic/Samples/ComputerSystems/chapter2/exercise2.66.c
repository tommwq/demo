#include <stdio.h>

int left_most_bit(unsigned int x) {
    return x & 0x
}

int main() {
    printf("%x %x\n", 0xff00, left_most_bit(0xff00));
    printf("%x %x\n", 0x6600, left_most_bit(0x6600));
    return 0;
}
