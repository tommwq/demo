#include <stdio.h>

void move(unsigned int count, const char *source, const char *destination){
  printf("MOVE %d FROM (%s) TO (%s)\n", count, source, destination);
}

void hanoi(unsigned int count, const char *source, const char *destination, const char *transit){
  if (count == 0){ return; }
  hanoi(count - 1, source, transit, destination);
  move(count, source, destination);
  hanoi(count - 1, transit, destination, source);
}

int main(){
  hanoi(3, "a", "b", "c");
  return 0;
}
