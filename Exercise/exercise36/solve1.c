#include <stdio.h>

int is_prime(int number);

int main() {
        for (int i = 0; i < 100; i++) {
                if (is_prime(i + 1)) {
                        printf("%d ", i + 1);
                }
        }
        return 0;
}

int is_prime(int number) {
        int half = number / 2;
        for (int i = 1; i < half; i++) {
                if (number % (i + 1) == 0) {
                        return 0;
                }
        }
        return 1;
}
