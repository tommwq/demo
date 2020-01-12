#include "tree.h"

struct Tree_node {
    Tree_node* parent;
    Tree_node* children;
    uint32_t children_number;
    void* value;
};

struct Tree {
    Tree_node* root;
};

void tree_insert(Tree* tree, Tree_node* parent, Tree_node* node);
