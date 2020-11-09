
#include "selectionSort.h"
#include "sortUtility.h"

#include <stdlib.h>

int selectionSort(void *buffer, size_t count, size_t size, int (*compare)(const void *, const void *)){
  void *tmp;
  int i, j, min;
  char *_buffer = (char *)buffer;

  if ((tmp = malloc(size)) == NULL){
    return -1;
  }

  for (i = 0; i < count; ++i){
    min = i;
    for (j = i + 1; j < count; ++j){
      if (compare(_buffer + j * size, _buffer + min * size) < 0){
        min = j;
      }
    }

    if (swap(_buffer + min * size, _buffer + i * size, size) == -1){
      return -1;
    }
  }
  
  return 0;
}
