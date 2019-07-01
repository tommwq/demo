#include <stdio.h>

int main(int argc, char *argv[]) {
    _asm INT 3;
    printf("Hello INT 3!\n");
    return 0;
}
