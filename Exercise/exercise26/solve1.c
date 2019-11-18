#include <stdio.h>

long factorial(long n) {
        if (n <= 1) {
                return 1;
        }

        return factorial(n - 1) * n;
}

int main() {
        printf("%d\n", factorial(5));
        
        return 0;
}
