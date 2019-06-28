/**
 * File: ForceRemoveAllSemaphores.c
 * Description: Remove all sempahores.
 * Author: Wang Qian
 * Version: 0.01
 * Create Date: 2016-08-18
 * Last Modified Date: 2016-08-18
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

void RemoveAllSemaphores();

// output: array of Semaphore
struct Array* EnumSemaphores();
// input: array of SemaphoreId, output: array of SemaphoreDetail
struct Array* EnumSemaphoreDetails(const struct Array *semids);

int ProcDirFilter(const struct dirent *item);
// ok: 0 ok, -1 , error
int ConvertToInteger(const char *s, int *ok);

int main(int argc, char *argv[]) {
  if (argc == 1) {
      RemoveAllSemaphores();
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

void RemoveAllSemaphores() {
  struct Array *sems = EnumSemaphores();
  if (sems == NULL || sems->count == 0) {
      fprintf(stdout, "NO SEMAPHORES.\n");
      return;
  }

  int count = sems->count;
  int i;
  int ok;
  int find;
  struct Semaphore *s;

  for (i = 0; i < count; i++) {
      s = (struct Semaphore*) ArrayGet(sems, i, &ok);
      if (ok == ERROR) {
          FATAL("fail to get array element");
      }
      union SemaphoreUnion result;
      result.val = 0;
      int ret = semctl(s->semid, 0, IPC_RMID, 0, result);
      if (ret == -1) {
          WARNING("fail to remove semaphore");
      }
  }

  ReleaseArray(sems);
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
