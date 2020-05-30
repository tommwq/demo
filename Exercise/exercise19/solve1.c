#include <stdio.h>

int is_complete_number(int number);

int main() {

        for (int i = 1; i <= 1000; i++) {
                if (is_complete_number(i)) {
                        printf("%d\n", i);
                }
        }

        return 0;
}

int is_complete_number(int number) {
        int factor_sum = 0;

        for (int i = 1; i < number; i++) {
                if (number % i == 0) {
                        factor_sum += i;
                        if (factor_sum > number) {
                                return 0;
                        }
                }
        }

        return factor_sum == number;
}
