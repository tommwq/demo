// busy_loop.c
// 循环调用系统调用，观察系统性能统计。
// 2019年03月25日 Wang Qian

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>

int main() {
    char name_template[16];
    snprintf(name_template, 16, "test_XXXXXX");
    const char *non_exist_name = mktemp(name_template);

    int fd = -1;

    while (fd == -1) {
        fd = open(non_exist_name, O_RDONLY);
    }

    // never go here
    close(fd);
}
