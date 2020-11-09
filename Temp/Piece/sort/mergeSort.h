
#ifndef _MERGESORT_H_
#define _MERGESORT_H_

#include <stdlib.h>

int mergeSort(void *buffer, size_t count, size_t size, int (*compare)(const void *, const void *));

#endif /* _MERGESORT_H_ */
