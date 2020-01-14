#pragma once

#include "build.h"
#include <stdarg.h>
#include <stdio.h>
#include <stdint.h>
#include <stdlib.h>
#include <string.h>
#include <wchar.h>

typedef void (*Visitor)(void* element, void* parameter);

#define panic() _panic(__FILE__, __LINE__)
void _panic(const char *file, int line);

#if defined(DEBUG)
#define debug(format, ...) _debug(__FILE__, __LINE__, format, __VA_ARGS__)
#else
#define debug(...)
#endif
void _debug(const char *file, int line, const char *format, ...);

#define create(t) (t*) malloc(sizeof(t))
#define delete(p) free(p)

enum Boolean {
    true = 0,
    false
};
typedef enum Boolean Boolean;

struct Buffer {
    void* offset;
    uint32_t size;
};
typedef struct Buffer Buffer;

struct Pair {
    void* first;
    void* second;
};
typedef struct Pair Pair;
