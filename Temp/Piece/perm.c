/*
  ´òÓ¡ÅÅÁĞ¡£
 */

#include <stdio.h>

/*
  [begin, end)
 */
void perm(int *array, int begin, int end){
  int i;
  int tmp;

  if (begin == end){
    for (i = 0; i < end; ++i){
      printf("%2d", array[i]);
    }
  } else {
    for (i = begin; i < end; ++i){
      /* swap array[i] and array[begin] */
      t = array[i];
      array[i] = array[begin];
      array[begin] = t;
      perm(array, begin + 1, end);
      /* swap array[i] and array[begin] */
      t = array[i];
      array[i] = array[begin];
      array[begin] = t;
    }    
  }
}

int main(void){
#define ARRAY_SIZE 8
  int array[ARRAY_SIZE];
  int i;

  for (i = 0; i < ARRAY_SIZE; ++i){
    array[i] = i + 1;
  }

  perm(array, 0, ARRAY_SIZE);

  return 0;
}
