#include <assert.h>
#include <limits.h>
#include <stdio.h>

// if not overflow, return 1, otherwise return 0.
int uadd_ok(unsigned int x, unsigned int y) {
    unsigned int z = x + y;

    if ((x > 0 && z < y) || (y > 0 && z < x)) {
        return 0;
    }

    return 1;
}

int main() {
    struct Test_case {
        unsigned int x;
        unsigned int y;
        int overflow;
    } test_cases[] = {
        {1, 1, 1},
        {UINT_MAX, 1, 0}
    };

    for (int i = 0; i < sizeof(test_cases) / sizeof(test_cases[0]); i++) {
        struct Test_case test_case = test_cases[i];
        unsigned int x = test_case.x;
        unsigned int y = test_case.y;
        int overflow = test_case.overflow;
        int result = uadd_ok(x, y);

        if (result != overflow) {
            printf("uadd_ok(%u, %u) = %d (%d expected)", x, y, result, overflow);
        }
    }

    return 0;
}
