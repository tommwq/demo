#pragma once

struct DList;
typedef struct DList DList;

struct DListElement;
typedef struct DListElement DListElement;

const int ok = 0;
const int error = -1;

void DList_init(DList *list, void (*destroy)(void *data));
void DList_destroy(DList *list);
int DList_insert_next(DList *list, DListElement *element, const void *data);
int Dlist_insert_previous(DList *list, DListElement *element, const void *data);
int DList_remove(DList *list, DListElement *element, void **data);
int DList_size(const DList *list);
DListElement *DList_head(const DList *list);
DListElement* DList_tail(const DList *list);
int DList_is_head(const DListElement *element);
int DList_is_tail(const DListElement *element);
void* DList_data(const DListElement *element);
DListElement* DList_next(const DListElement *element);
DListElement* DList_previous(const DListElement *element);

