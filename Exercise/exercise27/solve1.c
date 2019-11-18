#include <stdio.h>

void step(int n) {
        if (n <= 0) {
                return;
        }

        char ch;
        scanf("%c", &ch);
        step(n - 1);
        printf("%c", ch);
}

int main() {
        step(5);
}
