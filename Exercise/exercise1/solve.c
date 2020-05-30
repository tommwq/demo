#include <stdio.h>

int main() {

        int i, j, k;
        for (i = 0; i < 4; i++) {
                for (j = 0; j < 4; j++) {
                        for (k = 0; k < 4; k++) {
                                if (i != j && i != k && j != k) {
                                        printf("%d%d%d\n", i + 1, j + 1, k + 1);
                                }
                        }
                }
        }
        
        return 0;
}
