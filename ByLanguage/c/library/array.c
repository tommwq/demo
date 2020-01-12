#include "array.h"

struct Array {
    void* data;
    uint32_t length;
    uint32_t element_size;
};

Array* array_create(uint32_t element_size, uint32_t length) {

    Array* array = (Array*) malloc(sizeof(Array));
    if (array == NULL) {
        return NULL;
    }
    
    void* data = malloc(element_size * length);
    if (data == NULL) {
        free(array);
        return NULL;
    }

    array->data = data;
    array->length = length;
    array->element_size = element_size;

    return array;
}

void array_delete(Array *array) {
    if (array == NULL) {
        return;
    }

    free(array->data);
    array->length = 0;
    array->data = NULL;
    free(array);
}

uint32_t array_length(const Array *array) {
    if (array == NULL) {
        panic();
    }

    return array->length;
}

void array_set(Array *array, uint32_t position, void* element) {
    if (array == NULL || position > array_length(array)) {
        panic();
    }

    void* target = ((char*) array->data) + position * array->element_size;

    memcpy(target, element, array->element_size);
}

void* array_get(const Array *array, uint32_t position) {
    if (array == NULL || position > array_length(array)) {
        panic();
    }

    void* target = ((char*) array->data) + position * array->element_size;
    return target;
}

void array_visit(Array *array, ElementVisitor visitor, void *parameter) {
    if (array == NULL) {
        panic();
    }

    if (visitor == NULL) {
        return;
    }

    void* end = ((char*) array->data) + array->length * array->element_size;
    for (char* element = array->data; element != end; element += array->element_size) {
        visitor(element, parameter);
    }
}
