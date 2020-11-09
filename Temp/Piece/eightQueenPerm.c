
#include <stdio.h>

#define SIZE 8

int count = 0;

void printBoard(int *board){
  int i, j;

  for (i = 0; i < SIZE; ++i){
    for (j = 0; j < SIZE; ++j){
      printf(" %d", j == board[i] ? 1 : 0);
    }
    printf("\n");
  }
  printf("\n");
}

int checkBoard(int *board){
  int num[SIZE];
  int i, j;
  struct { int r; int c; } point [SIZE];

  for (i = 0; i < SIZE; ++i){ num[i] = 0; }

  for (i = 0; i < SIZE; ++i){
    int t = board[i];
    if (num[t] == 1){ return 0; }
    num[t] = num[t] + 1;
    point[i].r = i;
    point[i].c = t;
  }

  for (i = 0; i < SIZE; ++i){
    for (j = i + 1; j < SIZE; ++j){
      if (abs(point[i].r - point[j].r) == abs(point[i].c - point[j].c)){
        return 0;
      }
    }    
  }

  return 1;
}

void eightQueenPerm(int *board, int n){
  int i;

  if (n == SIZE){
    if (checkBoard(board)){
      ++count;
      printBoard(board);
    }
    return;
  }

  for (i = n; i < SIZE; ++i){
    int tmp;
    tmp = board[i];
    board[i] = board[n];
    board[n] = tmp;

    eightQueenPerm(board, n + 1);

    tmp = board[i];
    board[i] = board[n];
    board[n] = tmp;
  }
}

void initBoard(int *board){
  int i = 0;
  for (i = 0; i < SIZE ; ++i){
    board[i] = i;
  }
}

int main(){
  int result[SIZE];
  
  initBoard(result);
  eightQueenPerm(result, 0);

  printf("count: %d\n", count);

  return 0;
}
