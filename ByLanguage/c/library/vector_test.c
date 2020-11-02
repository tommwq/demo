
#include <stdio.h>

#include "vector.h"

void new_vector_ut(void){
  struct vector *vector = new_vector(0);
  printf("vector: %p length: %d\n", vector, vector_length(vector));
  delete_vector(vector);
  vector = new_vector(10);
  printf("vector: %p length: %d\n", vector, vector_length(vector));
  delete_vector(vector);
  vector = new_vector(100 * 10000);
  printf("vector: %p length: %d\n", vector, vector_length(vector));
  delete_vector(vector);

  vector = NULL;
  printf("vector: %p length: %d\n", vector, vector_length(vector));

}

void vector_resize_ut(void){
  int retval = 0;
  struct vector *vector = new_vector(0);
  
  retval = vector_resize(vector, 10);
  printf("vector: %p length: %d retval: %d\n", vector, vector_length(vector), retval);
  delete_vector(vector);

  vector = new_vector(10);
  retval = vector_resize(vector, 5);
  printf("vector: %p length: %d retval: %d\n", vector, vector_length(vector), retval);
  delete_vector(vector);

  vector = NULL;
  retval = vector_resize(vector, 10);
  printf("vector: %p length: %d retval: %d\n", vector, vector_length(vector), retval);
}

void vector_putget_ut(void){
  struct vector *vector = new_vector(10);
  int i1, i2, i3;
  int *pi;

  if (vector == NULL){
	fprintf(stderr, "create vector error\n");
	return;
  }

  pi = (int*)vector_put(vector, 0, &i1);
  i1 = 10;
  printf("*pi: %p %d il: %p %d\n", pi, *pi, &i1, i1);
  pi = (int*)vector_get(vector, 0);
  printf("*pi: %p %d il: %p %d\n", pi, *pi, &i1, i1);
  i1 = 9;
  printf("*pi: %p %d il: %p %d\n", pi, *pi, &i1, i1);
}


int main(void){

  new_vector_ut();  
  vector_resize_ut();
  vector_putget_ut();


  return 0;
}
