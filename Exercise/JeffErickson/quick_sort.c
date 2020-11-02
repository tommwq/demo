#include <stdio.h>
#include <stdlib.h>

void print(int *array, int size) {
    for (int i = 0; i < size; i++) {
        printf("% 2d", array[i]);
    }
    printf("\n");
}

void swap(int *array, int i, int j) {
    if (i == j) {
        return;
    }
    
    int t = array[i];
    array[i] = array[j];
    array[j] = t;
}

int partition(int *array, int size, int pivot_index) {
    swap(array, pivot_index, size - 1);
    int position = 0;
    for (int i = 0; i < size - 1; i++) {
        if (array[i] < array[size - 1]) {
            swap(array, position, i);
            position++;
        }
    }
    swap(array, position, size - 1);
    position++;
    return position;
}

void quick_sort(int *array, int size) {
    if (size <= 1) {
        return;
    }

    int pivot_index = 0;
    int middle = partition(array, size, pivot_index);
    quick_sort(array, middle);
    quick_sort(array + middle + 1, size - middle - 1);
}

int main() {
    int array[] = { 3, 4, 2, 1, 6, 5, 7 };
    print(array, 7);
    quick_sort(array, 7);
    print(array, 7);
    return 0;
}
