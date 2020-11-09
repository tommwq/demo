
#include <stdio.h>

void permutation(int *arr, unsigned int n, int start){
  int i;

  if (n == start){
    for (i = 0; i < n; ++i){
      printf("%d ", arr[i]);
    }
    printf("\n");
    return;
  }
  
  for (i = start; i < n; ++i){
    int tmp = arr[start];
    arr[start] = arr[i];
    arr[i] = tmp;

    permutation(arr, n, start + 1);

    tmp = arr[start];
    arr[start] = arr[i];
    arr[i] = tmp;
  }
}

int main(){
  int a[3];
  int i;

  for (i = 0; i < 3; ++i){
    a[i] = i;
  }

  permutation(a, 3, 0);
  return 0;
}
