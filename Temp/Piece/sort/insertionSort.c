
#include "insertionSort.h"
#include <stdlib.h>
#include "sortUtility.h"

int insertionSort(void *buffer, size_t count, size_t size, int (*compare)(const void *, const void *)){
  int i, j;
  char *_buffer = (char *)buffer;

  for (i = 1; i < count; ++i){
    for (j = i; 0 < j && compare(_buffer + j * size, _buffer + (j - 1) * size) < 0; --j){
      swap(_buffer + j * size, _buffer + (j - 1) * size, size);
    }
  }

  return 0;
}
