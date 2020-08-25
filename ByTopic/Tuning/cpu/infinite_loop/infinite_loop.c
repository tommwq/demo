// infinite_loop.c
// 执行死循环，观察系统性能数据。
// 2019年03月25日 Wang Qian

int calculate();
void black_hole(int x);

int main() {
    
    while (1) {
        black_hole(calculate());
    }
}

int calculate() {
    int x = 10;
    
    for (int i = 0; i < 10000; i++) {
        x = x * x;
    }

    return x;
}

void black_hole(int x) {
}
