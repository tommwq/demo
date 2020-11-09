#include <stdio.h>
#include <stdlib.h>

void print(int *array, int size) {
    for (int i = 0; i < size; i++) {
        printf("% 2d", array[i]);
    }
    printf("\n");
}

void merge(int *array, int size, int half) {
    int i = 0;
    int j = half;
    int k = 0;

    int *buffer = (int*) malloc(size * sizeof(int));
    while (i < half && j < size) {
        int a = array[i];
        int b = array[j];
        if (a < b) {
            buffer[k++] = a;
            i++;
        } else {
            buffer[k++] = b;
            j++;
        }
    }

    while (i < half) {
        buffer[k++] = array[i++];
    }

    while (j < size) {
        buffer[k++] = array[j++];
    }

    for (int i = 0; i < size; i++) {
        array[i] = buffer[i];
    }

    free(buffer);
}

void merge_sort(int *array, int size) {
    if (size <= 1) {
        return;
    }

    int half = size / 2;
    merge_sort(array, half);
    merge_sort(array + half, size - half);
    merge(array, size, half);
}

int main() {
    int array[] = { 3, 4, 2, 1, 6, 5, 7 };
    print(array, 7);
    merge_sort(array, 7);
    print(array, 7);
    return 0;
}
