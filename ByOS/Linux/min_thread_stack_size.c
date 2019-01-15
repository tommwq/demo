// -pthread
#include <stdio.h>
#include <limits.h>
#include <unistd.h>
#include <pthread.h>

int main(void) {

    pthread_attr_t attr;
    long min;
    long size;

    min = sysconf(_SC_THREAD_THREADS_MIN);
    printf("_SC_THREAD_THREADS_MIN: %ld\n", min);
    printf("PTHREAD_STACK_MIN: %ld\n", PTHREAD_STACK_MIN);

    if (pthread_attr_init(&attr) != 0) {
        perror("init error");
        return -1;
    }

    for (size = PTHREAD_STACK_MIN; size > 0; size--) {
        if (pthread_attr_setstacksize(&attr, _SC_THREAD_STACK_MIN) != 0) {
            size++;
            break;
        }
    }
    
    printf("min stack size: %ld\n", size);
    return 0;
}
