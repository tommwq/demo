/**
 * struct初始化。
 */

#include <stdio.h>

struct rect {
  int width;
  int length;
};

int main(void){

  struct rect r1 = { r1.width = 10, r1.length = 10 };

  printf("width: %d length: %d\n", r1.width, r1.length);

  return 0;
}
