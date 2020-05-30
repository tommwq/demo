#include <stdio.h>
#include <stdint.h>
#include <limits.h>

int signed_high_prod(int x, int y) {
    // 返回x*y的高32位。
    int64_t z = x * y;
    return z >> 32;
}

unsigned unsigned_high_prod(unsigned x, unsigned y) {
    unsigned x_sign = x >> 31;
    unsigned y_sign = y >> 31;
    
    return signed_high_prod((int) x, (int) y) + x_sign * y + y_sign * x + x_sign * y_sign;
}

int main() {
    printf("%d\n", unsigned_high_prod(0xffffffff, 2));
    return 0;
}
