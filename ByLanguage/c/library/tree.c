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

void tree_visit_pre_order(Tree_node *node, Visitor visitor, void* parameter);
void tree_visit_in_order(Tree_node *node, Visitor visitor, void* parameter);
void tree_visit_post_order(Tree_node *node, Visitor visitor, void* parameter);

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

    if (tree == NULL || visitor == NULL) {
        panic();
    }

    switch (visit_order) {
    case Pre_order:
        tree_visit_pre_order(tree->root, visitor, parameter);
        break;
    case In_order:
        tree_visit_in_order(tree->root, visitor, parameter);
        break;
    case Post_order:
        tree_visit_post_order(tree->root, visitor, parameter);
        break;
    default:
        break;
    }
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

void tree_visit_pre_order(Tree_node *node, Visitor visitor, void* parameter) {
    visitor(node->buffer.offset, parameter);
    List_node* list_node = list_head(node->children);
    while (list_node != NULL) {
        Tree_node *tree_node = (Tree_node*) list_node_get_value(list_node).offset;
        tree_visit_pre_order(tree_node, visitor, parameter);
        list_node = list_node_next(list_node);
    }
}

void tree_visit_in_order(Tree_node *node, Visitor visitor, void* parameter) {
    panic();
}

void tree_visit_post_order(Tree_node *node, Visitor visitor, void* parameter) {
    List_node* list_node = list_head(node->children);
    while (list_node != NULL) {
        Tree_node *tree_node = (Tree_node*) list_node_get_value(list_node).offset;
        tree_visit_post_order(tree_node, visitor, parameter);
        list_node = list_node_next(list_node);
    }
    visitor(node->buffer.offset, parameter);
}

