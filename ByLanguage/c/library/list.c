#pragma once
#include "list.h"

typedef struct List_node List_node;
struct List_node {
    Buffer buffer;
    List_node* next;
    List_node* previous;
};

struct List {
    List_node* head;
    List_node* tail;
    uint32_t length;
};

List* list_create() {
    List* list = create(List);
    if (list == NULL) {
        return NULL;
    }
    
    list->head = NULL;
    list->tail = NULL;
    list->length = 0;
    return list;
}

void list_delete(List *list) {
    if (list == NULL) {
        return;
    }

    // TODO
}

Boolean list_is_empty(const List *list) {
    if (list == NULL) {
        panic();
    }

    if (list->length == 0) {
        return true;
    } 

    return false;
}

uint32_t list_length(const List *list) {
    if (list == NULL) {
        panic();
    }

    return list->length;
}

void list_insert(List *list, List_node *node) {
    if (list == NULL || node == NULL) {
        panic();
    }

    if (list->length == 0) {
        list->head = node;
        list->tail = node;
        list->length = 1;
        node->previous = NULL;
        node->next = NULL;
        return;
    }

    node->previous = list->tail;
    list->tail->next = node;
    node->next = NULL;
    list->tail = node;
    list->length++;
}

/* void list_remove(List *list, List_node *node); */

void list_visit(List *list, Visitor visitor, void* parameter) {
    for (List_node* node = list->head; node != NULL; node = list_node_next(node)) {
        visitor(node->buffer.offset, parameter);
    }
}

List_node* list_node_create() {
    List_node* node = create(List_node);
    if (node == NULL) {
        return NULL;
    }
    node->next = NULL;
    node->previous = NULL;
    node->buffer.offset = NULL;
    node->buffer.size = 0;
    return node;
}

/* void list_node_delete(); */

Buffer list_node_get_value(List_node *node) {
    if (node == NULL) {
        panic();
    }
    return node->buffer;
}

void list_node_set_value(List_node *node, Buffer buffer) {
    if (node == NULL) {
        panic();
    }
    node->buffer = buffer;
}

List_node* list_node_next(const List_node* node) {
    if (node == NULL) {
        panic();
    }
    return node->next;
}

List_node* list_node_previous(const List_node* node) {
    if (node == NULL) {
        panic();
    }
    return node->previous;
}

