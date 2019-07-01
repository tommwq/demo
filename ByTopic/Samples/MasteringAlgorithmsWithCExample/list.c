#include "list.h"

struct ListElement {
    void *data;
    struct ListElement *next;
};

struct List {
    int size;
    int (*match)(const void *key1, const void *key2);
    void (*destroy)(void *data);
    struct ListElement *head;
    struct ListElement *tail;
};

int List_init(List *list, void (*destroy)(void *data)) {
    if (list == NULL) {
        return error;
    }
    
    list->size = 0;
    list->destroy = destroy;
    list->head = NULL;
    list->tail = NULL;

    return ok;
}

int list_destroy(List *list) {
    if (list == NULL) {
        return error;
    }

    void *data;
    while (List_size(list) > 0) {
        if (List_remove_next(list, NULL, (void**)&data) == 0 &&
            list->destroy != NULL) {
            list->destroy(data);
        }
    }

    memset(list, 0, sizeof(List));
    return ok;
}

int List_insert_next(List *list, ListElement *element, const void **data) {
    if (list == NULL) {
        return error;
    }

    ListElement *new_element;

    if ((new_element = (ListElement*) malloc(sizeof(ListElement))) == NULL) {
        return error;
    }

    new_element->data = (void *) data;
    list->size++;
        
    if (element == NULL) {
        // 将元素插入head之前
        if (List_size(list) == 0) {
            list->tail = new_element;
        }

        new_element->next = list->head;
        list->head = new_element;
        return ok;
    }

    // 其他位置插入
    if (element->next == NULL) {
        list->tail = new_element;
    }

    new_element->next = element->next;
    element->next = new_element;
    return ok;
}

int List_remove_next(List *list, ListElement *element, void **data) {
    ListElement *old_element;
    if (List_size(list) == 0) {
        return error;
    }

    if (element == NULL) {
        if (data != NULL) {
            *data = list->head->data;
        }

        old_element = list->head;
        list->head = list->head->next;
        if (List_size(list) == 1) {
            list->tail = NULL;
        }
        
        free(old_element);
        list->size--;
        return ok;
    }

    if (element->next == NULL) {
        return error;
    }

    if (data != NULL) {
        *data = element->next->data;
    }
    
    old_element = element->next;
    element->next = element->next->next;
    if (element->next == NULL) {
        list->tail = element;
    }

    free(old_element);
    list->size--;
    return ok;
}


int List_size(List *list) {
    if (list == NULL) {
        return error;
    }

    return list->size;
}


