#include <stdio.h>

int is_little_endian() {
    int i = 1;
    return *((char*) &i);
}

int main() {
    printf("is little endian: %d\n", is_little_endian());
    return 0;
}
