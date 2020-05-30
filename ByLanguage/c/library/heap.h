#pragma once

// TODO put to common.h
typedef int (*ElementVisitor)(void *element, void *parameter);
typedef unsigned int uint;
typedef int (*ElementComparer)(void *value1, void *value2, uint block_size);

struct Heap;
typedef struct Heap Heap;

Heap* heap_create(uint block_size, uint element_capacity, ElementComparer comparer);
void heap_delete(Heap *heap);
int heap_capacity(Heap *heap);
int heap_size(Heap *heap);
void* heap_top(Heap *heap);
void heap_pop(Heap *heap);
void heap_push(Heap *heap, void* value);

