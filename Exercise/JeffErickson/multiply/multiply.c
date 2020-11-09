#include <stdio.h>

void print_z(int z[6]) {
    for (int i = 0; i < 6; i++) {
        printf("%d", z[i]);
    }
    printf("\n");
}

void normal_multiply(int x[3], int y[3], int z[6]) {
    for (int j = 0; j < 3; j++) {
        for (int i = 0; i < 3; i++) {
            int k = 6 - 1 - (i + j);
            int v = x[3 - 1 - i] * y[3 - 1 - j];
            while (v > 0) {
                z[k] += (v % 10);
                v /= 10;
                if (z[k] > 9) {
                    v += z[k] / 10;
                    z[k] %= 10;
                }
                k--;
            }
        }
    }
}

void fibonacci_multiply(int x[3], int y[3], int z[6]) {
    int hold = 0;
    for (int k = 0; k < 3 + 3; k++) {
        for (int i = 0; i <= k && i < 3; i++) {
            int j = k - i;
            if (j < 3) {
                hold += x[3 - 1 - i] * y[3 - 1 - j];
            }
        }

        z[6 - 1 - k] = hold % 10;
        hold /= 10;
    }
}

// should print 293276
int main() {
    int x[3] = { 9, 3, 4 };
    int y[3] = { 3, 1, 4 };
    int z[6] = { 0 };

    normal_multiply(x, y, z);
    print_z(z);

    for (int i = 0; i < 6; i++) {
        z[i] = 0;
    }
    
    fibonacci_multiply(x, y, z);
    print_z(z);
    
    return 0;
}
