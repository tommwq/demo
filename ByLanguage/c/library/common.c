#include <stdio.h>
#include "common.h"

void _panic(const char *file, int line) {
    fprintf(stderr, "fatal error occured at %s:%d\n", file, line);
    abort();
}

void _debug(const char *file, int line, const char *format, ...) {
    fprintf(stderr, "[%s:%d] ", file, line);

    va_list argument_list;
    va_start(argument_list, format);
    vfprintf(stderr, format, argument_list);
    va_end(argument_list);
    fprintf(stderr, "\n");
}

