
#include <cstdio>
#include <cassert>
#include <windows.h>
#include <process.h>

bool cas1(void *object, void *oldValue, void *newValue){
  void *currentValue = InterlockedCompareExchangePointer(&object, 
                                                         oldValue,
                                                         newValue);
  return (currentValue == newValue);
}

bool cas(void **object, void *value, void *newValue){
  if (*object == value){
    *object = newValue;
    return true;
  }
  return false;
}

typedef struct Node {
  void *value;
  struct Node *next;
} Node;

Node *tail(0);
Node *head(0);

#define ENTER  { printf("enter %s\n", __FUNCTION__); fflush(stdout); }
#define LEAVE  { printf("leave %s\n", __FUNCTION__); fflush(stdout); }

void enqueue(void *data){
  Node *q = new Node;
  q->value = data;
  q->next = 0;
  bool success(false);

  Node *p;
  do {
    p = tail;
    success = cas((void **)&(p->next), 0, q);
    if (!success){
      cas((void **)&tail, p, p->next);
    }
  } while (success);
  cas((void **)&tail, p, q);
  if (head->next == 0){
    head->next = q;
  }
}

void* dequeue(){
  Node *p;

  do {
    p = head;
    if (p->next == 0){
      return 0;
    }
  } while (!cas((void **)&head, p, p->next));

  return p->next->value;
}

void consumer(void *v){
  static int id = 0;
  void *w;

  while (true){
    w = dequeue();
    if (w != 0){
      printf("-%d\n", *(int *)w); fflush(stdout);
      delete w;
    } else {
      break;
    }
    Sleep(rand() % 100);
  }
}

void producer(void *v){
  static int id = 0;

  int count(10);
  for (int i = 0; i < count; ++i){
    int *w = new int((i + 1) * *(int *)v);
    enqueue(w);
    printf("+%d\n", *w); fflush(stdout);
    // Sleep(rand() % 100);
  }
}

int main(){
  int consumerNumber(5);
  int producerNumber(10);
  HANDLE *handles = (HANDLE *)malloc(sizeof(HANDLE) * (consumerNumber + producerNumber));
  int k = 0;

  tail = (Node *)malloc(sizeof(*tail));
  head = (Node *)malloc(sizeof(*head));
  
  head->value = 0;
  head->next = 0;
  tail->value = 0;
  tail->next = 0;

  for (int i = 0; i < producerNumber; ++i){
    int *v = new int(i + 1);
    handles[k++] = (HANDLE)_beginthread(producer, 0, v);
  }

  for (int i = 0; i < consumerNumber; ++i){
    handles[k++] = (HANDLE)_beginthread(consumer, 0, 0);
  }

  WaitForMultipleObjects(k, handles, TRUE, INFINITE);
  return 0;
}
