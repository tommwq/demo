#pragma once

// TODO put to common.h
typedef int (*ElementVisitor)(void *element, void *parameter);
typedef unsigned int uint;
typedef int (*ElementComparer)(void *value1, void *value2, uint block_size);

struct Priority_queue;
typedef struct Priority_queue Priority_queue;

Priority_queue* priority_queue_create(uint block_size, ElementComparer comparer);
void priority_queue_delete(Priority_queue *priority_queue);
uint priority_queue_size(Priority_queue *priority_queue);
void* priority_queue_top(Priority_queue *priority_queue);
void priority_queue_pop(Priority_queue *priority_queue);
void priority_queue_push(Priority_queue *priority_queue, void* value);
