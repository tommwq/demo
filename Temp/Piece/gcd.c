
#include <stdio.h>

int gcd(int m, int n){
  if (n == 0){
    return m;
  }

  return gcd(n, m % n);
}

int main(void){
  printf("%d\n", gcd(15, 33));
  return 0;
}
