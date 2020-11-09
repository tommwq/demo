/*
 * iconv-non-thread-safe.cpp
 *
 * 展示iconv在多线程下可能引发SEGV的例子。
 *
 * g++ -pthread -ggdb iconv-non-thread-safe.cpp  && ./a.out
 */

#undef NDEBUG
#include <cassert>
#include <cstdio>
#include <string>
#include <iconv.h>
#include <pthread.h>

iconv_t cd = iconv_open("UTF-8", "GBK");

std::string gbk_to_utf8(const std::string &gbk){
  assert(cd != (iconv_t) -1);

  const size_t BUF_SIZE(128);
  char buffer[BUF_SIZE];

  char *in_buf = (char *) gbk.c_str();
  char *out_buf = buffer;
  size_t in_len = gbk.length();
  size_t out_len = BUF_SIZE;

  size_t result = iconv(cd, &out_buf, &in_len, &out_buf, &out_len);
  if (result == -1){
    //std::perror("");
    return "";
  }
  
  /*
   * 多线程环境下，此处的in_len和out_len是不安全的，使用out_len可能引发SIGSEGV。
   */
  std::string utf8(buffer, buffer + (BUF_SIZE - out_len));  
  return utf8;
}

void* worker(void *dummy){
  char buffer[] = { 0xc4, 0xe3, 0xba, 0xc3 }; // 你好
  std::string gbk_word(buffer, buffer + sizeof(buffer)); 
  for (int i = 0; i < 1000; ++i){
    std::string utf8_word = gbk_to_utf8(gbk_word);
  }

  return NULL;
}

int main(void){
  int const count(10);
  pthread_t thread[count];

  for (int i = 0; i < count; ++i){
    pthread_create(thread + i, NULL, worker, NULL);
  }

  for (int i = 0; i < count; ++i){
    pthread_join(thread[i], NULL);
  }

  iconv_close(cd);
  return 0;
}
