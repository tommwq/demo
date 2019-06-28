/**
 * File: ShowSemaphoreList.c
 * Description: File dangling semaphores, which sempid is not a valid process id anymore.
 * Author: Wang Qian
 * Version: 0.01
 * Create Date: 2016-08-15
 * Last Modified Date: 2016-08-16
 */

#include <errno.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/ipc.h>
#include <sys/sem.h>
#include <dirent.h>
#include <unistd.h>

void ShowHelp();
void ShowDanglingSemaphores();

#define FATAL(MESSAGE) Fatal(MESSAGE, __FILE__, __LINE__)
#define WARNING(MESSAGE) Warning("WARNING", MESSAGE, __FILE__, __LINE__)
void Warning(const char *tag, const char *message, const char *file, int line);
void Fatal(const char *message, const char *file, int line);

struct Array {
    int count;
    int blockSize;
    void *data;
};

struct Array* AllocArray(int count, int blockSize);
void ReleaseArray(struct Array *array);
// result: 0, ok, -1 error
void* ArrayGet(const struct Array *array, int position, int *result);
typedef int (*Less)(const void *, const void*);
#define NO_SUCH_ELEMENT -2
#define ERROR -1
#define OK 0
#define INVALID_VALUE -1
// -2: not found, -1: error, non-negative: index of element
int ArrayFind(const struct Array *array, int startPosition, void *target, Less less);

struct Semaphore {
    int semid;
    int nsems;
};

struct SemaphoreDetail {
    // -1 for invalid value
    int semid;
    int semnum;
    int pid;
};

union SemaphoreUnion {
    int val;
    struct semid_ds *buf;
    unsigned short *array;
    struct seminfo *__buf;
};

// output: array of Semaphore
struct Array* EnumSemaphores();
// input: array of SemaphoreId, output: array of SemaphoreDetail
struct Array* EnumSemaphoreDetails(const struct Array *semids);

typedef int ProcessId;
int ProcessIdLess(const void *lhs, const void *rhs);
// output: array of ProcessId
struct Array* EnumProcesses();

int ProcDirFilter(const struct dirent *item);
// ok: 0 ok, -1 , error
int ConvertToInteger(const char *s, int *ok);

int main(int argc, char *argv[]) {
  if (argc == 1) {
      ShowDanglingSemaphores();
      return 0;
  }

  ShowHelp();
  return 0;
}

void ShowHelp() {
  const char *message = 
      "Show danglling semaphores.\n"
      "usage:\n"
      "\t./FindDanglingSemaphore [-help]\n";
  fprintf(stdout, "%s\n", message);
}

void ShowDanglingSemaphores() {
  struct Array *sems = EnumSemaphores();
  if (sems == NULL || sems->count == 0) {
      fprintf(stdout, "NO SEMAPHORES.\n");
      return;
  }

  struct Array *details = EnumSemaphoreDetails(sems);
  if (details == NULL) {
      FATAL("cannot enumerate semaphore details");
  }
  struct Array *procs = EnumProcesses();
  if (procs == NULL) {
      FATAL("cannot enumerate processes");
  }

  int count = details->count;
  int i;
  int ok;
  int find;
  ProcessId pid;
  struct SemaphoreDetail *d;

  printf("SEMID\tSEMNUM\tPID\n");
  printf("------------------------\n");
  for (i = 0; i < count; i++) {
      d = (struct SemaphoreDetail*) ArrayGet(details, i, &ok);
      if (ok == ERROR) {
          FATAL("fail to get array element");
      }
      pid = d->pid;
      find = ArrayFind(procs, 0, &pid, ProcessIdLess);
      if (find == ERROR) {
          WARNING("cannot find element");
      } else if (find == NO_SUCH_ELEMENT) {
          printf("%d\t%d\t%d\n", d->semid, d->semnum, d->pid);
      }
  }
  printf("------------------------\n");

  ReleaseArray(sems);
  ReleaseArray(details);
  ReleaseArray(procs);
}

void Warning(const char *tag, const char *message, const char *file, int line) {
  const char *error = strerror(errno);
  printf("[%s] %s:%d: %s (%s).\n", tag, file, line, message, error);
}

void Fatal(const char *message, const char *file, int line) {
  Warning("FATAL", message, file, line);
  exit(ERROR);
}

struct Array* AllocArray(int count, int blockSize) {
  struct Array *array = (struct Array*) calloc(1, sizeof(struct Array));
  if (array == NULL) {
      return NULL;
  }
  int size = count * blockSize;
  array->data = calloc(count, blockSize);
  if (array->data == NULL) {
      free(array);
      return NULL;
  }
  array->count = count;
  array->blockSize = blockSize;
  return array;
}

void ReleaseArray(struct Array *array) {
  free(array->data);
  free(array);
}

void* ArrayGet(const struct Array *array, int position, int *result) {
  void *elem = NULL;
  int ret = INVALID_VALUE;

  do {
      if (array == NULL || array->data == NULL) {
          break;
      }
      if (position < 0 || position >= array->count) {
          break;
      }
      elem = ((char*) array->data) + (position * array->blockSize);
      ret = 0;
  } while (0);

  if (result != NULL) {
      *result = ret;
  }
  return elem;
}

struct Array* EnumSemaphores() {
  union SemaphoreUnion result;
  struct seminfo ignored;
  result.__buf = &ignored;
  int maxIndex = semctl(0, 0, SEM_INFO, result);
  if (maxIndex == ERROR) {
      FATAL("fail to call semctl");
  }

  struct Array *sems = AllocArray(maxIndex + 1, sizeof(struct Semaphore));
  if (sems == NULL) {
      return NULL;
  }

  if (maxIndex == 0) {
      return sems;
  }

  int ok;
  int ret;
  int i;
  struct Semaphore *s;
  struct semid_ds ds;
  result.buf = &ds;
  for (i = 0; i < maxIndex; i++) {
      s = (struct Semaphore*) ArrayGet(sems, i, &ok);
      if (ok == ERROR) {
          FATAL("fail to get array element");
      }
      ret = semctl(i, 0, SEM_STAT, result);
      if (ret == ERROR) {
          WARNING("fail to call semctl");
          s->semid = -1;
          continue;
      }
      s->semid = ret;
      s->nsems = ds.sem_nsems;
  }
  return sems;
}

struct Array* EnumSemaphoreDetails(const struct Array *sems) {
  if (sems == NULL) {
      return NULL;
  }

  int i;
  int ok;
  struct Semaphore *s;
  int detailCount = 0;
  for (i = 0; i < sems->count; i++) {
      s = (struct Semaphore*) ArrayGet(sems, i, &ok);
      if (ok == ERROR) {
          FATAL("fail to get array element");
      }
      detailCount += s->nsems;
  }

  struct Array *details = AllocArray(detailCount, sizeof(struct SemaphoreDetail));
  if (details == NULL) {
      return NULL;
  }

  int detailIndex = 0;
  int j;
  struct SemaphoreDetail *d;
  union SemaphoreUnion semun;
  semun.buf = NULL;
  for (i = 0; i < sems->count; i++) {
      s = (struct Semaphore*) ArrayGet(sems, i, &ok);
      if (ok == ERROR) {
          FATAL("fail to get array element");
      }
      for (j = 0; j < s->nsems; j++, detailIndex++) {
          d = (struct SemaphoreDetail*) ArrayGet(details, detailIndex, &ok);
          if (ok == ERROR) {
              FATAL("fail to get array element");
          }
          ok = semctl(s->semid, j, GETPID, semun);
          d->semid = s->semid;
          d->semnum = j;
          d->pid = ok;
          if (ok == -1) {
              WARNING("fail to get pid");
          }
      }
  }
  return details;
}

int ProcDirFilter(const struct dirent *item) {
  int ok;
  int value = ConvertToInteger(item->d_name, &ok);
  if (ok == OK) {
      return 1;
  }
  return 0;
}

struct Array* EnumProcesses() {
  struct Array *array = NULL;
  struct dirent **list;
  int size = scandir("/proc", &list, ProcDirFilter, alphasort);
  if (size < 0) {
      return NULL;
  }

  int i;
  int ok;
  ProcessId *pid;
  array = AllocArray(size, sizeof(ProcessId));
  do {
      if (array == NULL) {
          break;
      }
      while (size--) {
          pid = (ProcessId*) ArrayGet(array, size, &ok);
          if (ok == ERROR) {
              FATAL("fail to get array element");
          }
          *pid = ConvertToInteger(list[size]->d_name, NULL);
          free(list[size]);
      }
  } while (0);

  if (size > 0) {
      while (size--) {
          free(list[size]);
      }
  }
  free(list);

  return array;
}

int ArrayFind(const struct Array *array, int startPosition, void *target, Less less) {
  if (array == NULL || array->data == NULL) {
      return ERROR;
  }
  if (startPosition < 0) {
      return ERROR;
  }
  char *elem = (char*) array->data + (array->blockSize * startPosition);
  int i;
  for (i = startPosition; i < array->count; i++, elem += array->blockSize) {
      if (less(elem, target) == 0) {
          return i;
      }
  }
  return NO_SUCH_ELEMENT;
}

int ConvertToInteger(const char *s, int *ok) {
  int value = 0;
  int ret = -1;
  char buffer[256];
  value = atoi(s);
  int len = snprintf(buffer, 256, "%d", value);
  if (strlen(s) == len) {
      ret = 0;
  }
  if (ok != NULL) {
      *ok = ret;
  }
  return value;
}

int ProcessIdLess(const void *lhs, const void *rhs) {
  ProcessId l, r;
  l = *(ProcessId*) lhs;
  r = *(ProcessId*) rhs;
  
  if (l < r) {
      return -1;
  } else if (l == r) {
      return 0;
  } else {
      return 1;
  }
}
