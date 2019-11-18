#include <stdio.h>

// 手动计算结果为1534。

int main() {
        int days = 10 - 1;
        int last = 1;

        for (int i = 0; i < days; i++) {
                last = 2*(last + 1);
        }

        printf("%d\n", last);
        
        return 0;
}
