#pragma once

#include <stdio.h>
#include <stdlib.h>

#define ALLOC(T) (T*) calloc(1, sizeof(T))
#define RELEASE(p) free(p); (p) = NULL

#define ASSERT_NULL(p) if (p != NULL) abort()
#define ASSERT_NOT_NULL(p) if (p == NULL) abort()

#define DEBUG() fprintf(stderr, "source: %s:%d\n", __FILE__, __LINE__)
