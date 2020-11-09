

#include <stdio.h>
#include <stdlib.h>
#include <time.h>

void countingSort(int *data, int size){
  int bucket[10];
  int i, k;
  int *newData;

  for (i = 0; i < 10; ++i){
    bucket[i] = 0;
  }

  for (i = 0; i < size; ++i){
    bucket[data[i]]++;
  }

  newData = (int *)malloc(sizeof(int) * size);

  for (i = 0, k = 0; i < 10; ++i){
    while (bucket[i]-- > 0){
      newData[k++] = i;
    }
  }

  memcpy(data, newData, sizeof(int) * size);
  free(newData);
}

int less(const void *l, const void *r){
  return (*(int *)l - *(int *)r);
}

int main(void){
#define SIZE 10000
  int i;
  int data[SIZE];
  int baseline[SIZE];
  srand(time(0));

  for (i = 0; i < SIZE; ++i){
    data[i] = rand() % 10;
    baseline[i] = data[i];
  }

  countingSort(data, SIZE);
  qsort(baseline, SIZE, sizeof(int), less);

  printf("result: %s", (memcmp(data, baseline, sizeof(int) * SIZE) == 0) ? "right" : "wrong");
  
  return 0;
}
