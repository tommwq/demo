#include <math.h>
#include <stdio.h>

int is_narcissistic_number(int number);

int main() {
        for (int i = 100; i < 1000; i++) {
                if (is_narcissistic_number(i)) {
                        printf("%d\n", i);
                }
        }
        
        return 0;
}

int is_narcissistic_number(int number) {
        if (number < 100 || number > 999) {
                return 0;
        }

        int a = number / 100;
        int b = (number / 10) % 10;
        int c = number % 10;

        return pow(a, 3) + pow(b, 3) + pow(c, 3) == number;
}
