#include "tree.h"
#include "list.h"

struct Tree_node {
    Tree_node* parent;
    List* children;
    uint32_t children_number;
    Buffer buffer;
};

struct Tree {
    Tree_node* root;
};

Tree* tree_create() {
    Tree* tree = create(Tree);
    if (tree != NULL) {
        return NULL;
    }
    
    tree->root = NULL;
    return tree;
}

void tree_delete(Tree *tree) {
    // TODO
}

void tree_visit(Tree *tree, Visitor visitor, void* parameter, Tree_visit_order visit_order) {
    // TODO
}

void tree_insert(Tree* tree, Tree_node* parent, Tree_node* node) {
    if (tree == NULL || parent == NULL || node == NULL) {
        panic();
    }

    node->parent = parent;
    
    List_node* list_node = list_node_create();
    Buffer buffer;
    buffer.offset = node;
    buffer.size = sizeof(void*);
    list_node_set_value(list_node, buffer);
    list_insert(parent->children, list_node);
}

void tree_remove(Tree* tree, Tree_node* node);

Tree_node* tree_node_create() {
    Tree_node *node = create(Tree_node);
    if (node == NULL) {
        return NULL;
    }
    node->parent = NULL;
    node->children = list_create();
    node->children_number = 0;
    node->buffer.offset = NULL;
    node->buffer.size = 0;
    return node;
}

Buffer tree_node_get_value(Tree_node* node) {
    if (node == NULL) {
        panic();
    }
    return node->buffer;
}

void tree_node_set_value(Tree_node* node, Buffer buffer) {
    if (node == NULL) {
        panic();
    }
    node->buffer = buffer;
}
