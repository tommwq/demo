#include <stdio.h>

typedef unsigned char *byte_pointer;

void show_bytes(byte_pointer start, size_t len) {
    size_t i;
    for (i = 0; i < len; i++) {
        printf(" %.2x", start[i]);
    }
    printf("\n");
}

#define show(variable) show_bytes((byte_pointer) &variable, sizeof(variable))

int main() {

    int i = 1;
    float f = 1.0;
    double d = 1.0;

    show(i);
    show(f);
    show(d);
    
    return 0;
}
