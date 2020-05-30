#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <limits.h>

void* my_calloc(size_t nmemb, size_t size) {
    size_t n = nmemb * size;
    if (n == 0) {
        return NULL;
    }
    
    void* buffer = malloc(n);
    if (buffer == NULL) {
        return NULL;
    }

    memset(buffer, 0x00, n);
    return buffer;
}

int main() {
    return 0;
}
