
#ifndef _SELECTIONSORT_H_
#define _SELECTIONSORT_H_

#include <stdlib.h>
#include "sort.h"

int selectionSort(void *buffer, size_t count, size_t size, int (*compare)(const void *, const void *));


#endif /* _SELECTIONSORT_H_ */
