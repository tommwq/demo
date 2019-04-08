#pragma once

#include <stdlib.h>


struct ListElement;
typedef struct ListElement ListElement;

struct List;
typedef struct List List;

const int ok = 0;
const int error = -1;

int List_init(List *list, void (*destroy)(void *data));
int List_destroy(List *list);
// ���element��NULL��������ͷ������
int List_insert_next(List *list, ListElement *element, const void *data);
// ���element��NULL��������ͷ�Ƴ�
int List_remove_next(List *list, ListElement *element, void **data);
int List_size(List *list);


#define List_head(list) ((list)->head)
#define List_tail(list) ((list)->tail)
#define List_is_head(list, element) ((element) == (list)->head ? 1 : 0)
#define List_is_tail(list, element) ((element)->next == NULL ? 1 : 0)
#define List_data(element) ((element)->data)
#define List_next(element) ((element)->next)


