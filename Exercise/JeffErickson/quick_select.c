#include <stdio.h>
#include <stdlib.h>

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

int quick_select(int *array, int size, int topK) {
    if (size == 1) {
        return array[0];
    }

    int middle = partition(array, size, 0);
    if (middle = topK) {
        return array[middle];
    } else if (middle < topK) {
        return quick_select(array + middle, size - middle, topK - middle);
    } else {
        return quick_select(array, middle, topK);
    }
}

int main() {
    int array[] = { 3, 4, 2, 1, 6, 5, 7 };
    int third = quick_select(array, 7, 3 - 1);
    printf("third element: %d\n", third);
    return 0;
}
