
struct ListElement;
typedef struct ListElement ListElement;



struct ListElement {
    void *data;
    struct ListElement *next;
};

int ListRemoveNext(List *list, ListElement *element, void **data);
int ListInsertNext(List *list, ListElement *element, void **data);

