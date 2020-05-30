#include <stdio.h>

void fib(int n);

int main() {

        fib(12);
        return 0;
}

void swap(int *a, int *b) {
        int t = *a;
        *a = *b;
        *b = t;
}

void fib(int n) {
        int a, b;
        a = 0;
        b = 1;

        for (int i = 0; i < n; i++) {
                swap(&a, &b);
                a = a + b;
                printf("%d ", a);
        }
}
