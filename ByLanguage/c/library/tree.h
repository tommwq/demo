#pragma once

// TODO put to common.h
typedef int (*ElementVisitor)(void *element, void *parameter);
typedef unsigned int uint;

struct Tree;
typedef struct Tree Tree;

Tree* tree_create(uint block_size, uint element_capacity);
void tree_delete(Tree *tree);
int tree_visit(Tree *tree, ElementVisitor visitor);
