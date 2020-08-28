
#include <stdlib.h>
#include <limits.h>
#include <stdio.h>
#include <time.h>

#include "sort.h"
#include "bubbleSort.h"
#include "selectionSort.h"
#include "insertionSort.h"
#include "mergeSort.h"
#include "quickSort.h"

struct Sort {
  Sorter _sorter;
  const char *_sortName;
  clock_t _time;
  int _result;
};

int main(){
  int i;
  int size;
  int count;
  void *data;
  void *bufferBaseLine;
  clock_t start, stop, elapsed;

  struct Sort sorts[] = {
    { bubbleSort, "Bubble Sort", 0 },
    { selectionSort, "Selection Sort", 0 },
    { insertionSort, "Insertion Sort", 0 },
    { mergeSort, "Merge Sort", 0 },
    { quickSort, "Quick Sort", 0 },
  };

  srand(time(0));
  count = 10000;
  size = count * sizeof(char);

  data = generateData(size);  
  bufferBaseLine = copyBuffer(data, size);
  start = clock();
  qsort(bufferBaseLine, count, sizeof(char), less);
  stop = clock();
  elapsed = stop - start;

  for (i = 0; i < sizeof(sorts) / sizeof(struct Sort); ++i){
    int ret;
    void *buffer;
    struct Sort *sort;

    sort = sorts + i;
    buffer = copyBuffer(data, size);
    start = clock();
    sort->_sorter(buffer, count, sizeof(char), less);
    stop = clock();
    sort->_time = stop - start; 
    ret = bufferCompare(bufferBaseLine, buffer, size);
    sort->_result = ret;

    {
      int k = 0;
      printf("%s done\n", sort->_sortName); 
      fflush(stdout);
      /*
        for (k = 0; k < count; ++k){
        printf("%d ", *((char *)buffer + k));
        }
        printf("\n");
        fflush(stdout);
      */
    } 

    free(buffer);
  }

  {
    int k;
    char *header[] = { "Name: ", "Time: ", "Correct: " };
    struct Sort sort;
    for (k = 0; k < sizeof(header) / sizeof(char *); ++k){
      printf("%10s", header[k]);
      if (k == 0){
        printf("%16s", "qsort");
      } else if (k == 1){
        printf("%16.4f", elapsed * 1.0 / CLOCKS_PER_SEC);
      } else {
        printf("%16s", "yes");
      }

      for (i = 0; i < sizeof(sorts) / sizeof(struct Sort); ++i){
        sort = sorts[i];
        switch (k){
        case 0:
          printf(" | %16s", sort._sortName);
          break;
        case 1:
          printf(" | %16.4f", sort._time * 1.0 / CLOCKS_PER_SEC); 
          break;
        case 2:
          printf(" | %16s", sort._result == 1 ? "yes" : "no");
          break;
        default:
          break;
        }
      }
      printf("\n");
    }
  }

  return 0;
}
