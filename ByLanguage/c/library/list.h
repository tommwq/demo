#pragma once

// TODO put to common.h
typedef int (*ElementVisitor)(void *element, void *parameter);

struct List;
typedef struct List List;

List* list_create();
void list_delete(List *list);

int list_is_empty(const List *list);
List* list_insert(List *list, const void *element);
void* list_remove(List *list, List *element);
void* list_element_value(List *element);
List* list_next(const List *element);
List* list_previous(const List *element);
int list_visit(List *list, ElementVisitor visitor);
int list_length(const List *list);

