#include <stdio.h>
#include "array.h"

void print_int(void* int_value, void* null_value) {
    printf("%d ", *((int*) int_value));
}

int main() {

    Array* array = array_create(sizeof(int), 3);

    int* i1 = create(int);
    int* i2 = create(int);
    int* i3 = create(int);
    *i1 = 1;
    *i2 = 2;
    *i3 = 3;
    
    array_set(array, 0, i1);
    array_set(array, 1, i2);
    array_set(array, 2, i3);
    array_visit(array, print_int, NULL);
    array_delete(array);
    delete(i1);
    delete(i2);
    delete(i3);
    
    return 0;
}
