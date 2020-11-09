/**
 * supervisor.cpp
 * supervisor示例。
 */

#include <cerrno>
#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <csignal>
#include <string>
#include <sstream>
#include <unistd.h>
#include <fcntl.h>
#include <sys/types.h>
#include <sys/wait.h>

bool running = true;
pid_t pid = 0;

void master_signal_handler(int signal_number){
  running = false;
  if (pid != 0){
    if (kill(pid, SIGTERM) == -1){
      perror("");
    }
  }
}

void slave_signal_handler(int signal_number){
  running = false;
}

void run_master(){
  signal(SIGTERM, master_signal_handler);
  while (running){
    if ((pid = fork()) == 0){
      execl("a.out", "a.out", "slave", NULL);
    } else if (pid > 0){
      waitpid(pid, NULL, 0);
    }
  }
}

void run_slave(){
  signal(SIGTERM, slave_signal_handler);
  int fd = open("log", O_WRONLY | O_CREAT | O_APPEND);
  while (running){
    std::ostringstream os;
    os << getpid() << "  is working.";
    std::string line = os.str();
    write(fd, line.c_str(), line.length());
    usleep(1000 * 1000);
  }
}

int main(int argc, char **argv){
  int master = 1;
  if (argc > 1 && strcmp(argv[1], "slave") == 0){
    master = 0;
  }
  if (master){
    run_master();
  } else {
    run_slave();
  }
  return 0;
}
