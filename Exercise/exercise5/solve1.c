#include <stdio.h>
#include <stdlib.h>

int less_int(const void *a, const void *b);

int main() {

        int num[3];
        scanf("%d %d %d", num, num + 1, num + 2);
        qsort(num, 3, sizeof(int), less_int);
        
        for (int i = 0; i < 3; i++) {
                printf("%d ", num[i]);
        }
        printf("\n");
        
        return 0;
}

int less_int(const void *a, const void *b) {
        int x = *(int*) a;
        int y = *(int*) b;
        return x - y;
}
