#include <stdio.h>

// 手动计算结果为199.8046875

int main() {
        double height = 100;
        int times = 10;
        double path = 0;
        for (int i = 0 ; i < times; i++) {
                path += height;
                height /= 2;
        }

        printf("%.4f", path);
        return 0;
}
