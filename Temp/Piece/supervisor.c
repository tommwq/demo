/**
 * supervisor.c
 * supervisor示例。
 */

#include <errno.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <signal.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/types.h>

int main(int argc, char **argv){
  int log_fd = open("start_log", O_WRONLY | O_APPEND | O_CREAT);
  write(log_fd, argv[0], strlen(argv[0]));
  if (argc == 1){
    write(log_fd, " *\n", 3);
    return 0;
  }
  write(log_fd, " ", 1);
  write(log_fd, argv[1], strlen(argv[1]));
  write(log_fd, "\n", 1);
  close(log_fd);
  int master = 1;
  if (strcmp(argv[1], "slave") == 0){
    master = 0;
  }
  char * const arg[] = { "slave", NULL };
  if (master){
    pid_t pid;
    while (1){
      pid = fork();
      if (pid == 0){
        if (execl("a.out", "a.out", "slave", NULL) == -1){
          const char *error = strerror(errno);
          int fd = open("./log", O_WRONLY | O_CREAT | O_APPEND);
          write(fd, error, strlen(error));
          write(log_fd, " #\n", 3);
        }
      } else if (pid > 0){
        waitpid(pid, NULL, 0);
      }
    }
  } else {
    int fd = open("./log", O_WRONLY | O_CREAT | O_APPEND);
    int running = 1;
    if (fd == -1){
      perror("");
      running = 0;
    }
    while (running){
      if (write(fd, "OK\n", 3) == -1){
        perror("");
      }
      usleep(1000 * 1000);
    }
  }
  return 0;
}
