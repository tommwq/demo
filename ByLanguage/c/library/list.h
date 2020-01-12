#pragma once
#include "common.h"

struct List;
typedef struct List List;

struct List_node;
typedef struct List_node List_node;

List* list_create();
void list_delete(List *list);
Boolean list_is_empty(const List *list);
void list_insert(List *list, List_node *node);
void list_remove(List *list, List_node *node);
void list_visit(List *list, Visitor visitor, void* parameter);
uint32_t list_length(const List *list);


List_node* list_node_create();
void list_node_delete(List_node* node);
Buffer list_node_get_value(List_node *node);
void list_node_set_value(List_node *node, Buffer buffer);
List_node* list_node_next(const List_node* node);
List_node* list_node_previous(const List_node* node);
