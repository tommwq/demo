
#include "bubbleSort.h"

int bubbleSort(void *buffer, size_t count, size_t size, int (*compare)(const void *, const void *)){
  int i, j;
  char *_buffer;
  
  if (buffer == NULL 
      || count == 0
      || size == 0
      || compare == NULL){
    return -1;
  }

  _buffer = (char *)buffer;
  for (i = count - 1; i > 0; --i){
    for (j = 0; j < i; ++j){
      if (compare(_buffer + (j + 1) * size, _buffer + j * size) < 0){
        if (swap(_buffer + j * size, _buffer + (j + 1) * size, size) == -1){
          return -1;
        }
      }
    }
  }
  
  return 0;
}
