/**
 * stack_trace.cpp
 * 示例打印堆栈。
 *
 * 用法：使用objdump -d a.out，查看汇编码。在程序崩溃后，根据堆栈偏移量，找到对应的函数。
 */
#include <csignal>
#include <cstdio>
#include <cstdlib>
#include <execinfo.h>

void signal_handler(int signum){
  const int SIZE = 100;
  void *buffer[SIZE];
  int depth = backtrace(buffer, SIZE);
  char **trace = backtrace_symbols(buffer, depth);
  if (trace != NULL){
    for (int i = 0; i < depth; ++i){
      printf("%s\n", trace[i]);
    }
    free(trace);
  }
  std::exit(0);
}


int main(){
  signal(SIGSEGV, signal_handler);
  *(int *) 0x00 = 1;
  return 0;
}
