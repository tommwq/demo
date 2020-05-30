#include <stdio.h>
#include <limits.h>

int saturating_add(int x, int y) {
    int z = x + y;
    if (x > 0 && y > 0 && z < 0) {
        return INT_MAX;
    }

    if (x < 0 && y < 0 && z > 0) {
        return INT_MIN;
    }

    return z;
}

int main() {
    printf("%d\n", saturating_add(INT_MAX, 2));
    printf("%d\n", saturating_add(INT_MIN, -1));
    return 0;
}
