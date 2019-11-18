#include <stdio.h>

int main() {

        int m, n;
        scanf("%d %d", &m, &n);

        int gcd = greatest_common_divisor(m, n);
        int lcm = least_common_multiple(m, n);

        printf("gcd = %d\nlcm = %d\n", gcd, lcm);
        
        return 0;
}

int greatest_common_divisor(int m, int n) {
        int a = m > n ? m : n;
        int b = m < n ? m : n;
        int r;

        while ((r = a % b) != 0) {
                a = b;
                b = r;
        }

        return b;
}

int least_common_multiple(int m, int n) {
        int a = m > n ? m : n;
        int b = m < n ? m : n;
        int result = a;

        for (result = a; result % b != 0; result += a) {
                NULL;
        }

        return result;
}
