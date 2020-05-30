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

void DList_init(DList *list, void (*destroy)(void *data)) {
    list->size = 0;
    list->destroy = destroy;
    list->head = NULL;
    list->tail = NULL;

    return;
}

void DList_destroy(DList *list) {
    void *data;

    while (DList_size(list) > 0) {
        if (DList_remove(list, DList_tail(list), (void **)&data) == 0 && list->destroy != NULL) {
            list->destroy(data);
        }
    }

    memset(list, 0, sizeof(DList));
    return;
}

int DList_insert_next(DList *list, DListElement *element, const void *data) {
    DListElement *new_element;

    if (element == NULL && DList_size(list) != 0) {
        return -1;
    }

    if ((new_element = (DListElement*) malloc(sizeof(DListElement))) == NULL) {
        return -1;
    }

    new_element->data = (void*) data;
    if (DList_size(list) == 0) {
        list->head = new_element;
        list->head->previous = NULL;
        list->head->next = NULL;
        list->tail = new_element;
    } else {
        new_element->next = element->next;
        new_element->previous = element;

        if (element->next == NULL) {
            list->tail = new_element;
        } else {
            element->next->previous = new_element;
        }

        element->next = new_element;
    }

    list->size++;
    return 0;
}

int DList_insert_previous(DList *list, DListElement *element, const void *data) {
    DListElement *new_element;

    if (element == NULL && DList_size(list) != 0) {
        return -1;
    }

    if ((new_element = (DListElement*) malloc(sizeof(DListElement))) == NULL) {
        return -1;
    }

    new_element->data = (void*) data;
    if (DList_size(list) == 0) {
        list->head = new_element;
        list->head->previous = NULL;
        list->head->next = NULL;
        list->tail = new_element;
    } else {
        new_element->next = element;
        new_element->previous = element->previous;
        if (element->previous == NULL) {
            list->head = new_element;
        } else {
            element->previous->next = new_element;
        }

        element->previous = new_element;
    }

    list->size++;
    return 0;
}

int DList_remove(DList *list, DListElement *element, void **data) {
    if (element == NULL || DList_size(list) == 0) {
        return -1;
    }

    *data = element->data;
    if (element == list->head) {
        list->head = element->next;
        if (list->head == NULL) {
            list->tail = NULL;
        } else {
            element->next->previous = NULL;
        }
    } else {
        element->previous->next = element->next;
        if (element->next == NULL) {
            list->tail = element->previous;
        } else {
            element->next->previous = element->previous;
        }
    }

    free(element);
    list->size--;
    return 0;
}
