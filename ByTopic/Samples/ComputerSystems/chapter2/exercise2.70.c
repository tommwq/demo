#include <stdio.h>

int is_fit_bits(int x, int n) {
    unsigned int z = ~((0x01 << n) - 1);
    int y = (z & (unsigned int) x) && 1;
    return 1 - y;
}

int main() {
    printf("%d\n", is_fit_bits(5, 2));
    printf("%d\n", is_fit_bits(5, 4));
    return 0;
}
