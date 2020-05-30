#include <stdio.h>
#include <limits.h>

int tsub_ok(int x, int y) {

    int z = x - y;
    if (x > 0 && y < 0 && z < 0) {
        return 0;
    }

    if (x < 0 && y > 0 && z > 0) {
        return 0;
    }

    return 1;
}

int main() {
    printf("%d\n", tsub_ok(2, 1));
    printf("%d\n", tsub_ok(INT_MIN, 1));
    return 0;
}
