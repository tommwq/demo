#ifndef _SORT_H_
#define _SORT_H_

#include "sortUtility.h"

typedef int (*Sorter)(void *buffer, size_t count, size_t size, int (*)(const void *, const void *));

#endif /* _SORT_H_ */
