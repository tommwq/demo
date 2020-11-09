
#include <cstdio>
#include <string>
#include <sys/time.h>

std::string current_time_string(){
  struct timeval tv;
  if (gettimeofday(&tv, NULL) != 0){
    return "";
  }

  struct tm tm;
  if (localtime_r(&tv.tv_sec, &tm) == NULL){
    return "";
  }

  std::string time_buffer(32, '\0');
  strftime((char *) time_buffer.c_str(), time_buffer.length(), "%Y/%m/%d %H:%M:%S", &tm);

  std::string buffer(32, '\0');
  
  sprintf((char *) buffer.c_str(), "%s.%03d", time_buffer.c_str(), tv.tv_usec / 1000);

  return buffer; 
}



void my_debug(const char *message){
  /* version 1
  fprintf(stderr, "%s: %s\n", current_time_string().c_str(), message);
  */

  /* version 2 */
  struct timeval tv;
  if (gettimeofday(&tv, NULL) != 0){
    tv.tv_sec = 0;
    tv.tv_usec = 0;
  }

  fprintf(stderr, "%d.%d: %s\n", tv.tv_sec, tv.tv_usec, message);
}

int main(){
	for (int i = 0; i < 30; ++i){
		my_debug("abc");
		usleep(1000 * 100);
	}
}
