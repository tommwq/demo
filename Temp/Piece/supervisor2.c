
#include <unistd.h>
#include <sys/wait.h>
#include <signal.h>
#include <stdlib.h>

int g_waiting = 1;
pid_t pid;

void supervisor(){

  do {
    if ((pid = fork()) < 0){
      return;
    } else if (pid == 0){
      // child process
      return;
    } else {
      // parent process
      waitpid(pid, NULL, 0);
    }
  } while (g_waiting);
}

void sig(int s){
  g_waiting = 0;
  kill(pid, SIGTERM);
  waitpid(pid, NULL, 0);
  exit(0);
}

int main(void){
  signal(SIGTERM, sig);
  supervisor();
  while (1){
    sleep(1);
  }
  return 0;
}
