#include <stdio.h>
#include <stdlib.h>
#include "triplex_storage.h"

int main() {
    struct TriplexStorage *storage = open_triplex_storage(".", 4 * PAGE_SIZE);
    if (storage == NULL) {
        fprintf(stderr, "´íÎó£º´ò¿ª´æ´¢Ê§°Ü¡£\n");
        return -1;
    }

    char page[PAGE_SIZE] = {0};
    int result;

    result = read_page(storage, 0, page);
    if (result != 0) {
        fprintf(stderr, "´íÎó£º½¨Á¢»º³åÇøÊ§°Ü¡£\n");
        return -1;
    }
    page[5] = '\0';
    fprintf(stdout, "page = \"%s\"\n", page);

    strncpy(page, "hello", 5);
    result = write_page(storage, 0, page);
    if (result != 0) {
        fprintf(stderr, "´íÎó£ºĞ´Ò³Ê§°Ü¡£\n");
        return -1;
    }
        
    return 0;
}
