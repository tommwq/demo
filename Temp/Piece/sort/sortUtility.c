
#include "sortUtility.h"
#include <stdlib.h>

int swap(void *x, void *y, size_t length){
  void *t;

  if (x == NULL
      || y == NULL
      || length == 0){
    return -1;
  }

  if ((t = malloc(length)) == NULL){
    return -1;
  }

  memcpy(t, x, length);
  memcpy(x, y, length);
  memcpy(y, t, length);
  free(t);
  return 0;
}

int less(const void *left, const void *right){
  return (*(char *)left - *(char *)right);
}

void *copyBuffer(void *buffer, size_t length){
  void *newBuffer;

  if (buffer == NULL || length == 0){
    return NULL;
  }
  newBuffer = malloc(sizeof(int) * length);
  if (newBuffer == NULL){
    return NULL;
  }

  memcpy(newBuffer, buffer, length);
  return newBuffer;
}

int bufferCompare(void *buffer1, void *buffer2, size_t length){
  int i;

  if (length == 0){
    return 1;
  }

  if (buffer1 == NULL || buffer2 == NULL){
    return 0;
  }

  return (memcmp(buffer1, buffer2, length) == 0);
}

void *generateData(size_t length){
  void *buffer;
  int i;
  int *_buffer;

  if (length == 0){
    return NULL;
  }

  buffer = malloc(length);
  if (buffer == NULL){
    return NULL;
  }

  _buffer = (char *)buffer;
  for (i = 0; i < length; ++i){
    *((char *)buffer + i) =  (char)(rand() % CHAR_MAX);
  }

  return buffer;
}
