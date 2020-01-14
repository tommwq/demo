#include <stdio.h>

int is_int_shift_arithmetic() {
    return (-1 >> 1) == -1;
}

int main() {

    printf("is int shift arithmetic: %d\n", is_int_shift_arithmetic());
    
    return 0;
}
