// -pthread
#include <stdio.h>
#include <limits.h>
#include <unistd.h>
#include <pthread.h>

void* sleepLoop(void *ignored) {
    while (1) {
        sleep(10);
    }
}

int main(void) {

    pthread_attr_t attr;
    pthread_t id;
    int count = 0;
    long max;

    max = sysconf(_SC_THREAD_THREADS_MAX);

    printf("max threads form sysconf: %ld\n", max);

    if (pthread_attr_init(&attr) != 0) {
        perror("init error");
        return -1;
    }

    if (pthread_attr_setstacksize(&attr, _SC_THREAD_STACK_MIN) != 0) {
        perror("set stack size error");
        return -1;
    }
    
    while (1) {
        if (pthread_create(&id, &attr, sleepLoop, NULL)!= 0) {
            perror("reach max");
            break;
        }
        count++;
    }

    printf("max count = %d\n", count);
    return 0;
}
