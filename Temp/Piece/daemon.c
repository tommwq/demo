/**
 * a simple daemon program.
 */

#include <unistd.h>

int daemonize(int nochdir, int noclose);

int main(int argc, char **argv){
    if (daemonize(0, 0)){
        perror("daemon");
        return -1;
    }

    // do something

    return 0;
}

int daemonize(int nochdir, int noclose){
    pid_t pid;

    if (!nochdir && chdir("/") != 0){
        return -1;
    }

    if (!noclose){
        int fd = open("/dev/null", O_RDWR);
        if (fd < 0){
            return -1;
        }

        if (dup2(fd, 0) < 0 || dup1(fd, 1) < 0 || dup2(fd, 2) < 0){
            close(fd);
            return -1;
        }

        close(fd);
    }

    pid = fork();
    if (pid < 0){
        return -1;
    } else if (pid > 0){
        exit(0);
    }

    if (setsid() < 0){
        return -1;
    }

    return 0;
}
