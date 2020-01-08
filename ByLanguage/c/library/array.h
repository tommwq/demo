#pragma once

// TODO put to common.h
typedef int (*ElementVisitor)(void *element, void *parameter);
typedef unsigned int uint;

struct Array;
typedef struct Array Array;

Array* array_create(uint block_size, uint element_capacity);
void array_delete(Array *array);
int array_set(Array *array, uint position, void *value);
void* array_get(const Array *array, uint position);
int array_visit(Array *array, ElementVisitor visitor);
