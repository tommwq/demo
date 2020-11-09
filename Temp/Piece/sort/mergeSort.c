
#include "mergeSort.h"
#include <stdlib.h>
#include <stdio.h>
#include "sortUtility.h"

static int merge(void *buffer, size_t count, size_t size, 
                 int (*compare)(const void *, const void *), size_t middle);

int mergeSort(void *buffer, size_t count, size_t size, int (*compare)(const void *, const void *)){
  int middle = count / 2;

  if (count <= 1){
    return 0;
  }

  mergeSort(buffer, middle, size, compare);
  mergeSort((char *)buffer + middle * size, count - middle, size, compare);
  merge(buffer, count, size, compare, middle);

  return 0;
}

int merge(void *buffer, size_t count, size_t size, int (*compare)(const void *, const void *), size_t middle){
  int i, j, k;
  char *tmpBuffer;
  char *_buffer = (char *)buffer;

  if ((tmpBuffer = (char *)malloc(count * size)) == NULL){
    return -1;
  }

  for (k = 0, i = 0, j = middle; i < middle && j < count; ++k){
    if (compare(_buffer + i * size, _buffer + j * size) < 0){
      memcpy(tmpBuffer + k * size, _buffer + i * size, size);
      ++i;
    } else {
      memcpy(tmpBuffer + k * size, _buffer + j * size, size);
      ++j;
    }
  }
  while (i < middle){
    memcpy(tmpBuffer + k * size, _buffer + i * size, size);
    ++i;
    ++k;
  }
  while (j < count){
    memcpy(tmpBuffer + k * size, _buffer + j * size, size);
    ++j;
    ++k;
  }

  memcpy(buffer, tmpBuffer, count * size);
  free(tmpBuffer);

  return 0;
}
