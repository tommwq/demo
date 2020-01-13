#include <assert.h>
#include <limits.h>
#include <stdio.h>

// if not overflow, return 1, otherwise return 0.
int tadd_ok(int x, int y) {
    int z = x + y;

    if (z < 0 && x > 0 && y > 0) {
        return 0;
    } else if (z > 0 && x < 0 && y < 0) {
        return 0;
    }

    return 1;
}

int main() {
    struct Test_case {
        int x;
        int y;
        int overflow;
    } test_cases[] = {
        {1, 1, 1},
        {INT_MAX, 1, 0},
        {INT_MIN, -1, 0}
    };

    for (int i = 0; i < sizeof(test_cases) / sizeof(test_cases[0]); i++) {
        struct Test_case test_case = test_cases[i];
        int x = test_case.x;
        int y = test_case.y;
        int overflow = test_case.overflow;
        int result = tadd_ok(x, y);

        if (result != overflow) {
            printf("tadd_ok(%d, %d) = %d (%d expected)", x, y, result, overflow);
        }
    }

    return 0;
}
