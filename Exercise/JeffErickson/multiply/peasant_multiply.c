#include <stdio.h>

unsigned int peasant_multiply(unsigned int x, unsigned int y) {
    unsigned int prod = 0;
    while (x > 0) {
        if (x % 2 == 1) {
            prod += y;
        }
        x /= 2;
        y += y;
    }

    return prod;
}

int main() {

    unsigned int result = peasant_multiply(123, 456);
    // should print 56088
    printf("%u\n", result);
    
    return 0;
}
