
#include <stdio.h>

int recursiveSum(int *a, int n){
  if (n == 0){
    return 0;
  }
  return a[0] + recursiveSum(a + 1, n - 1);
}

int main(void){
#define N 5
  int n[N];
  int i;

  for (i = 0; i < N; ++i){
    n[i] = i + 1;
  }

  printf("sum: %d\n", recursiveSum(n, N));

  return 0;
}
