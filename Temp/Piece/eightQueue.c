#include <stdio.h>

#define SIZE 8

enum BoardState { blank, queen };
int count = 0;

void printBoard(int *board){
  int i = 0;
  for (i = 0; i < (SIZE) * (SIZE) + 0; ++i){
    printf(" %d", board[i]);
    if ((i + 1) % SIZE == 0){
      printf("\n");
    }
  }
  printf("\n");

  ++count;
  if (count >= 100){
    printf("count: %d\n", count);
    exit(0);
  }
}

void eightQueue(int *board, unsigned int row){
  int column;

  if (row == SIZE){
    printBoard(board);
    return;
  }

  for (column = 0; column < SIZE; ++column){
    if (canChess(board, row, column)){ 
      board[row * SIZE + column] = queen; 
      eightQueue(board, row + 1);
      board[row * SIZE + column] = blank; 
    }
  }
}

int canChess(int *board, int row, int column){
  int r, c;
  for (r = 0; r < row; ++r){ if (board[r * SIZE + column] == queen){ return 0; } }
  for (r = row, c = column; 0 < r && 0 < c; --r, --c){ if (board[r * SIZE + column] == queen){ return 0; } }
  for (r = row, c = column; 0 < r && 0 < c; --r, ++c){ if (board[r * SIZE + column] == queen){ return 0; } }

  return 1;
}

/*
void printBoard(int board[SIZE][SIZE]){
  int i;
  int j;

  for (i = 0; i < SIZE; ++i){
    for (j = 0; j < SIZE; ++j){
      printf(" %d", board[i][j]);
    }
    printf("\n");
  }
  printf("\n");
}
*/

/*
void initBoard(int board[SIZE][SIZE]){
  int i;
  int j;

  for (i = 0; i < SIZE; ++i){
    for (j = 0; j < SIZE; ++j){
      board[i][j] = blank;
    }
  }
}
*/

void initBoard(int *board){
  int i;
  for (i = 0; i < SIZE * SIZE; ++i){
    board[i] = blank;
  }
}

int main(){
  int board[SIZE][SIZE];

  initBoard(board);
  eightQueue(board, 0);

  printf("count: %d\n", count);

  return 0;
}
