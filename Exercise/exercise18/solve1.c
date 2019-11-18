#include <stdio.h>
#include <stdlib.h>

int main(int argc, char *argv[]) {

        /*
          a = 2, n = 4
          2222
          0222
          0022
          0002
          sum = 2468
        */

        int a, n;
        int sum = 0;
        scanf("%d %d", &a, &n);

        for (int i = 0; i < n; i++) {
                sum += a * (4 - i);
                a *= 10;
        }

        printf("sum = %d\n", sum);
        
        return 0;
}

