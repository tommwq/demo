#include <stdio.h>

int main(){

  int ch;

  for (ch = 0; ch < 256; ++ch){
    char c = ch;
    printf("%c\n", c);
  }

  printf("\n");

  return 0;
}