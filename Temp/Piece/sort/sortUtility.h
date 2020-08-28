#ifndef _SORTUTILITY_H_
#define _SORTUTILITY_H_

#include <stdlib.h>

int swap(void *x, void *y, size_t length);
int less(const void *left, const void *right);
int bufferCompare(void *buffer1, void *buffer2, size_t length);
void *copyBuffer(void *buffer, size_t length);
void *generateData(size_t length);

#endif /* _SORTUTILITY_H_ */
