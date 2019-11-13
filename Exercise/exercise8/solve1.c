#include <stdio.h>

int main() {

        for (int i = 0; i < 9; i++) {
                for (int j = 0; j <= i; j++) {
                        printf("%d*%d=%d", j + 1, i + 1, (i + 1) * (j + 1));
                        printf("%c", j == i ? '\n' : ' ');
                }
        }

        return 0;
}

