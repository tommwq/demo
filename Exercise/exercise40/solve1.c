#include <stdio.h>

int main() {
        int array[] = { 1, 2, 3, 4, 5 };
        for (int i = 0; i < 5; i++) {
                printf("%d ", array[4 - i]);
        }
        return 0;
}
