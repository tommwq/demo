#include <stdio.h>
#include "list.h"

void print_int(void* int_value, void* null_value) {
    printf("%d ", *((int*) int_value));
}

int main() {

    List* list = list_create();

    int* i1 = create(int);
    int* i2 = create(int);
    int* i3 = create(int);
    *i1 = 1;
    *i2 = 2;
    *i3 = 3;
    Buffer buffer;

    List_node* node1 = list_node_create();
    buffer.offset = (char*) i1;
    buffer.size = sizeof(int);
    list_node_set_value(node1, buffer);

    List_node* node2 = list_node_create();
    buffer.offset = (char*) i2;
    buffer.size = sizeof(int);
    list_node_set_value(node2, buffer);

    List_node* node3 = list_node_create();
    buffer.offset = (char*) i3;
    buffer.size = sizeof(int);
    list_node_set_value(node3, buffer);
    
    list_insert(list, node1);
    list_insert(list, node2);
    list_insert(list, node3);

    list_visit(list, print_int, NULL);
    list_delete(list);
    
    delete(i1);
    delete(i2);
    delete(i3);
    
    return 0;
}
