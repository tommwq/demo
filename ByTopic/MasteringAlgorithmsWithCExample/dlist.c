#include "dlist.h"
#include <stdlib.h>

struct DListElement {
    void *data;
    struct DListElement *previous;
    struct DListElement *next;
};

struct DList {
    int size;
    int (*match)(const void *key1, const void *key2);
    void (*destroy)(void *data);
    DListElement *head;
    DListElement *tail;
};


int DList_size(const DList *list) {
    if (list == NULL) {
        return error;
    }
    
    return list->size;
}

DListElement* DList_head(const DList *list) {
    if (list == NULL) {
        return NULL;
    }

    return list->head;
}

DListElement* DList_tail(const DList *list) {
    if (list == NULL) {
        return NULL;
    }

    return list->tail;
}

int DList_is_head(const DListElement *element) {
    if (element == NULL) {
        return error;
    }

    return element->previous == NULL ? 1 : 0;
}

int DList_is_tail(const DListElement *element) {
    if (element == NULL) {
        return error;
    }

    return element->next == NULL ? 1 : 0;
}

void* DList_data(const DListElement *element) {
    if (element == NULL) {
        return NULL;
    }

    return element->data;
}

DListElement* DList_next(const DListElement *element) {
    if (element == NULL) {
        return NULL;
    }

    return element->next;
}

DListElement* DList_previous(const DListElement *element) {
    if (element == NULL) {
        return NULL;
    }

    return element->previous;
}
