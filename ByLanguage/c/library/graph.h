#pragma once

// TODO put to common.h
typedef int (*ElementVisitor)(void *element, void *parameter);
typedef unsigned int uint;

struct Graph;
typedef struct Graph Graph;

Graph* graph_create(uint block_size, uint element_capacity);
void graph_delete(Graph *graph);
int graph_visit(Graph *graph, ElementVisitor visitor);
