/*
  PrintWinInfo.c
  打印Windows系统信息。
 */

#include <stdio.h>

#include <TCHAR.h>
#include <Windows.h>

int main(int argc, char **argv){

  _tprintf(TEXT("%-20s = %u\n"), TEXT("MAX_PATH"), MAX_PATH);

  return 0;
}
