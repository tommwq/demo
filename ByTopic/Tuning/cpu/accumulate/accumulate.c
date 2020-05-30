// accumulate.c
// 采用单线程和双线程的方式计算累加，观察运行时间。
// 2019年03月25日

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <pthread.h>

enum run_mode_t {
    single_thread,
    dual_thread,
    dual_thread_pad_cacheline
};

enum run_mode_t parse_run_mode(const char *arg);
void single();
void dual();
void dual_pad();

void* dual_routine(void *ignore);
void* dual_routine_pad(void *ignore);

const int count = 100 * 10000;
pthread_mutex_t lock = PTHREAD_MUTEX_INITIALIZER;
int x = 0;
long long ignore1;
long long ignore2;
long long ignore3;
long long ignore4;
pthread_mutex_t lock_pad = PTHREAD_MUTEX_INITIALIZER;

int main(int argc, char *argv[]) {
    enum run_mode_t run_mode = single_thread;

    if (argc > 0) {
        run_mode = parse_run_mode(argv[1]);
    }

    switch (run_mode) {
    case single_thread:
        single();
        break;
    case dual_thread:
        dual();
        break;
    case dual_thread_pad_cacheline:
        dual_pad();
        break;
    default:
        abort();
    }
}

enum run_mode_t parse_run_mode(const char *arg) {
    
    if (strcmp("dual", arg) == 0) {
        return dual_thread;
    }

    if (strcmp("dual_pad", arg) == 0) {
        return dual_thread_pad_cacheline;
    }

    return single_thread;
}

void single() {
    struct timespec start, stop;
    if (clock_gettime(CLOCK_MONOTONIC, &start) == -1) {
        abort();
    }
    for (int i = 0; i < count; i++) {
        x += i;
    }
    if (clock_gettime(CLOCK_MONOTONIC, &stop) == -1) {
        abort();
    }
    double elapsed = (stop.tv_sec - start.tv_sec) * 1000 + (stop.tv_nsec - start.tv_nsec) / 1000000.0;

    printf("elapsed: %.2f ms\n", elapsed);
}

void dual() {
    pthread_t t1, t2;

    struct timespec start, stop;
    if (clock_gettime(CLOCK_MONOTONIC, &start) == -1) {
        abort();
    }

    if (pthread_create(&t1, NULL, dual_routine, NULL) != 0) {
        abort();
    }
    if (pthread_create(&t2, NULL, dual_routine, NULL) != 0) {
        abort();
    }
    
    pthread_join(t1, NULL);
    pthread_join(t2, NULL);
    if (clock_gettime(CLOCK_MONOTONIC, &stop) == -1) {
        abort();
    }
    
    double elapsed = (stop.tv_sec - start.tv_sec) * 1000 + (stop.tv_nsec - start.tv_nsec) / 1000000.0;

    printf("elapsed: %.2f ms\n", elapsed);
}

void dual_pad() {
    pthread_t t1, t2;

    struct timespec start, stop;
    if (clock_gettime(CLOCK_MONOTONIC, &start) == -1) {
        abort();
    }

    if (pthread_create(&t1, NULL, dual_routine_pad, NULL) != 0) {
        abort();
    }
    if (pthread_create(&t2, NULL, dual_routine_pad, NULL) != 0) {
        abort();
    }
    
    pthread_join(t1, NULL);
    pthread_join(t2, NULL);
    if (clock_gettime(CLOCK_MONOTONIC, &stop) == -1) {
        abort();
    }
    
    double elapsed = (stop.tv_sec - start.tv_sec) * 1000 + (stop.tv_nsec - start.tv_nsec) / 1000000.0;

    printf("elapsed: %.2f ms\n", elapsed);
}

void* dual_routine(void *ignore) {
    for (int i = 0; i < count / 2; i++) {
        pthread_mutex_lock(&lock);
        x += i;
        pthread_mutex_unlock(&lock);
    }
}

void* dual_routine_pad(void *ignore) {
    for (int i = 0; i < count / 2; i++) {
        pthread_mutex_lock(&lock_pad);
        x += i;
        pthread_mutex_unlock(&lock_pad);
    }
}
