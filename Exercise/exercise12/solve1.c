#include <stdio.h>

int main() {
        int prime[100]; // prime[i] 表示 i+101

        for (int i = 0; i < 100; i++) {
                prime[i] = 1;
        }

        for (int i = 0; i < 100; i++) {
                int p = 2;
                while (p < 100) {
                        int n = i + 101;
                        if (n % p == 0) {
                                prime[i] = 0;
                        }

                        if (p == 2) {
                                p++;
                        } else {
                                p += 2;
                        }
                }
        }

        int count = 0;
        for (int i = 0; i < 100; i++) {
                if (prime[i]) {
                        count++;
                        printf("%d\n", i + 101);
                }
        }

        return 0;
}
