#pragma once
#include "common.h"

struct Array;
typedef struct Array Array;

Array* array_create(uint32_t element_size, uint32_t length);
void array_delete(Array *array);
void array_set(Array *array, uint32_t position, void* element);
void* array_get(const Array *array, uint32_t position);
void array_visit(Array *array, ElementVisitor visitor, void *parameter);
uint32_t array_length(const Array *array);
