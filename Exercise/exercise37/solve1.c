#include <stdio.h>
#include <stdlib.h>

int less(void *lhs, void *rhs);

int main() {
        int array[] = { 5, 3, 7, 9, 1, 0, 4, 8, 2, 6 };
        qsort(array, 10, sizeof(int), less);

        for (int i = 0; i < 10; i++) {
                printf("%d ", array[i]);
        }
        
        return 0;
}

int less(void *lhs, void *rhs) {
        return *(int*)lhs - *(int*)rhs;
}
