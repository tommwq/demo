#ifndef _BUBBLESORT_H_
#define _BUBBLESORT_H_

#include <stdlib.h>
#include "sort.h"

int bubbleSort(void *buffer, size_t count, size_t size, int (*compare)(const void *, const void *));

#endif /* _BUBBLESORT_H_ */
