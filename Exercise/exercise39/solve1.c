#include <stdio.h>
#include <stdlib.h>

int* find(int *array, int length, int target);

int main() {

        int array[] = { 1, 2, 4, 5, 6, -1 };
        int target = 3;
        int length = 5;
        int *position = find(array, length, target);
        for (int *i = array + length; i != position; i--) {
                *i = *(i - 1);
        }
        *position = target;

        for (int i = 0; i < length + 1; i++) {
                printf("%d ", array[i]);
        }

        return 0;
}

int* find(int *array, int length, int target) {
        if (length == 0) {
                return array;
        }
        
        int middle = length / 2;
        int *pivot = array + middle;
        if (target == *pivot) {
                return pivot;
        } else if (target < *pivot) {
                return find(array, length / 2 - 1, target);
        } else {
                return find(pivot + 1, length - length / 2 - 1, target);
        }
}
