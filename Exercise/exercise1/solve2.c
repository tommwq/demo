#include <stdio.h>

typedef void (*callback_t)(void*);

void permutation(int *array, int first, int last, callback_t cb);
void combination(int *array, int length, int combin_length, callback_t cb);

void combin_perm(void *param);
void print_result(void *param);

int main() {
        int input[] = {1, 2, 3, 4};

        combination(input, 4, 3, combin_perm);
                    
        return 0;
}

void permutation(int *array, int first, int last, callback_t cb) {
        if (first == last) {
                cb (array);
                return;
        }

        for (int i = first; i <= last; i++) {
                int tmp = array[first];
                array[first] = array[i];
                array[i] = tmp;

                permutation(array, first + 1, last, cb);

                tmp = array[first];
                array[first] = array[i];
                array[i] = tmp;
        }
}

void combin_perm(void *param) {
        /* printf("comb: "); */
        /* print_result(param); */

        int *array = (int*) param;
        permutation(array, 0, 2, print_result);
}
        
void print_result(void *param) {
        int *array = (int*) param;
        for (int i = 0; i < 3; i++) {
                printf("%d", array[i]);
        }
        printf("\n");
}

void combine(int *input, int input_size, int *output, int output_size, callback_t cb, void *param);

void combination(int *array, int length, int combin_length, callback_t cb) {
        int *buffer = (int*) malloc(sizeof(int) * combin_length);
        if (buffer == NULL) {
                abort();
        }
        
        combine(array, length, buffer, combin_length, cb, buffer);
        free(buffer);
}

void combine(int *input, int input_size, int *output, int output_size, callback_t cb, void *param) {
        if (output_size == 0) {
                cb(param);
                return;
        }

        if (input_size == 0) {
                return;
        }

        output[0] = input[0];
        combine(input + 1, input_size - 1, output + 1, output_size - 1, cb, param);
        
        combine(input + 1, input_size - 1, output, output_size, cb, param);
}
