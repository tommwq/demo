#include <stdio.h>

int main() {
        double sum;
        double factorial = 1;

        for (int i = 0; i < 20; i++) {
                factorial *= (i + 1);
                sum += factorial;
        }

        printf("%.4f", sum);
        
        return 0;
}
