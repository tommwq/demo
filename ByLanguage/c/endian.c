/*
 * endian.c
 * 判断机器是大端还是小端。
 */
#include <stdio.h>
#include <stdlib.h>

union Foo{
    int i;
    char c;
};

int main(void){
    union Foo foo;
    foo.i = 1;
    if (foo.c == 1){
        printf("little endian\n");
    } else {
        printf("big endian\n");
    }
    return EXIT_SUCCESS;
}
