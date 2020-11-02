/*
 * vector.h
 * 顺序存储的线性表。
 * 作为数据结构的一个小练习。
 */

/*
 TODO:
   26/4 增加几个get/put测试用例。
   26/4 编写vector_insert。
*/

#ifndef VECTOR_H
#define VECTOR_H


struct vector;

struct vector* new_vector(int size);
void delete_vector(struct vector *vector);
int vector_length(struct vector *vector);
int vector_resize(struct vector *vector, int new_size);
void* vector_put(struct vector *vector, int index, void *element);
void* vector_get(struct vector *vector, int index);
int vector_insert(struct vector *vector, int index, void *element);
int vector_remove(struct vector *vector, int index);
void vector_sort(struct vector *vector);
int vector_find(struct vector *vector, int (*compare_function)(void *element));
int vector_copy(struct vector *to_vector, struct vector *from_vector);
int vector_append(struct vector *vector, struct vector *vector_append);

#endif

