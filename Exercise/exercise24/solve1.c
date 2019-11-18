#include <stdio.h>

int main() {
        double sum = 0.0;
        int denominator = 1;
        int numerator = 2;
        int tmp;
        
        for (int i = 0; i < 20; i++) {
                printf("%d/%d ", numerator, denominator);
                sum += numerator / denominator;
                tmp = numerator;
                numerator += denominator;
                denominator = tmp;

        }

        printf("\n");
        printf("%.4f", sum);

        return 0;
}

