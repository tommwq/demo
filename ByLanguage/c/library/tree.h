#pragma once

struct Tree;
typedef struct Tree Tree;

struct Tree_node;
typedef struct Tree_node;

Tree* tree_create();
void tree_delete(Tree *tree);
void tree_visit(Tree *tree, Visitor visitor, void* parameter);

void tree_insert(Tree* tree, Tree_node* parent, Tree_node* node);

