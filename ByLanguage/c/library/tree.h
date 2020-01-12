#pragma once
#include "common.h"

struct Tree;
typedef struct Tree Tree;

struct Tree_node;
typedef struct Tree_node Tree_node;

enum Tree_visit_order {
    Pre_order,
    In_order,
    Post_order
};
typedef enum Tree_visit_order Tree_visit_order;

Tree* tree_create();
void tree_delete(Tree *tree);
void tree_visit(Tree *tree, Visitor visitor, void* parameter, Tree_visit_order visit_order);

void tree_insert(Tree* tree, Tree_node* parent, Tree_node* node);
void tree_remove(Tree* tree, Tree_node* node);

Tree_node* tree_node_create();
Buffer tree_node_get_value(Tree_node* node);
void tree_node_set_value(Tree_node* node, Buffer buffer);
