#include <stdio.h>

int is_prime(int number);

int main() {

        int number = 90;
        int remain = number;
        for (int i = 2; i < number; NULL) {
                if (!is_prime(i)) {
                        i++;
                        continue;
                }

                if (remain % i == 0) {
                        printf("%d\n", i);
                        remain /= i;
                } else {
                        i++;
                }
        }
        
        return 0;
}

int is_prime(int number) {
        if (number > 2 && number % 2 == 0) {
                return 0;
        }
        
        int half = number / 2;
        for (int i = 3; i < half; i += 2) {
                if (number % i == 0) {
                        return 0;
                }
        }

        return 1;
}
