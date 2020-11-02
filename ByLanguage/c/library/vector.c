/*
 * vector.c
 * 实现顺序存储的线性表。
 */

#include "vector.h"

#include <stdlib.h>

struct vector{
  int       size;
  void      **element;
};

struct vector* new_vector(int size){
  struct vector *vector = (struct vector*)malloc(sizeof(struct vector));
  if (vector == NULL){
	return NULL;
  }

  if (size < 0){
	size = 0;
  }

  vector->size = size;
  vector->element = NULL;
  if (vector->size == 0){
	return vector;
  }

  vector->element = malloc(sizeof(void*) * vector->size);
  if (vector->element == NULL){
	free(vector);
	return NULL;
  }

  return vector;
}

void delete_vector(struct vector *vector){
  if (vector == NULL){
	return;
  }

  free(vector->element);
  vector->element = NULL;
  vector->size = 0;
  free(vector);
}

int vector_length(struct vector *vector){
  if (vector == NULL){
	return -1;
  }

  return vector->size;
}

int vector_resize(struct vector *vector, int new_size){
  void *element;

  if (vector == NULL){
	return -1;
  }

  element = malloc(sizeof(void*) * new_size);
  if (element == NULL){
	return vector->size;
  }

  free(vector->element);
  vector->element = element;
  vector->size = new_size;
  return new_size;
}

void* vector_put(struct vector *vector, int index, void *element){
  if (vector == NULL){
    return NULL;
  }

  if (index < 0 || vector_length(vector) <= index){
	return NULL;
  }

  vector->element[index] = element;
  return element;
}

void* vector_get(struct vector *vector, int index){
  if (vector == NULL){
    return NULL;
  }

  if (index < 0 || vector_length(vector) <= index){
	return NULL;
  }

  return vector->element[index];
}
