
#include "quickSort.h"
#include "sortUtility.h"

#include <stdio.h>

int quickSort(void *buffer, size_t count, size_t size, int (*compare)(const void *, const void *)){
  int lower, upper, middle;
  int pivot;
  char *_buffer = (char *)buffer;

  if (count <= 1){
    return 0;
  }

  //  printf("quick count: %d\n", count); fflush(stdout);

  pivot = 0;
  for (lower = 1, upper = count - 1; lower <= upper; NULL){
    while (lower <= upper && compare(_buffer + lower * size, _buffer + pivot * size) < 0){
      ++lower;
    }
    while (lower <= upper && compare(_buffer + pivot * size, _buffer + upper * size) < 0){
      --upper;
    }
    
    if (lower < upper){
      swap(_buffer + lower * size, _buffer + upper * size, size);
      ++lower;
      --upper;
    } else {
      break;
    }
  }
  
  middle = 0;
  if (upper > 0){
    swap(_buffer + upper * size, _buffer + pivot * size, size);
    middle = upper;
  }

  if (0 < middle){
    quickSort(buffer, middle, size, compare);
  }

  if (1 + middle < count){
    quickSort(_buffer + (middle + 1) * size, count - middle - 1, size, compare);
  }
  return 0;
}
