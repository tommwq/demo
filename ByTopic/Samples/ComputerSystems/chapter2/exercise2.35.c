#include <assert.h>
#include <limits.h>
#include <stdio.h>

// if not overflow, return 1, otherwise return 0.
int tmult_ok(int x, int y) {
    if (x == 0) {
        return 1;
    }
    
    int p = x * y;
    return p / x == y;
}

int main() {
    struct Test_case {
        int x;
        int y;
        int overflow;
    } test_cases[] = {
        {1, 1, 1},
        {INT_MAX, 2, 0},
        {INT_MIN, -2, 0}
    };

    for (int i = 0; i < sizeof(test_cases) / sizeof(test_cases[0]); i++) {
        struct Test_case test_case = test_cases[i];
        int x = test_case.x;
        int y = test_case.y;
        int overflow = test_case.overflow;
        int result = tmult_ok(x, y);

        if (result != overflow) {
            printf("tmult_ok(%d, %d) = %d (%d expected)", x, y, result, overflow);
        }
    }

    return 0;
}
