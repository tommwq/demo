
#include <cstdio>
#include <cstdlib>
#include <list>
#include <windows.h>
#include <process.h>

template<typename T>
class List {
public:
  List<T>(){}
  virtual ~List<T>(){}
public:
  virtual void push(T *t) = 0;
  virtual T* pop() = 0;
};

template<typename T>
class List1: public List<T> {
public:
  template<typename T>
  struct Node {
    T *_value;
    Node<T> *_next;
    Node<T>()
    :_value(0), _next(0){}
  };
  List1<T>(){
    head._value = 0;
    head._next = 0;
    tail._value = 0;
    tail._next = 0;
    mutex = CreateMutex(0, 0, 0);
  }
  ~List1<T>(){
    while (pop() != 0){}
  }
  virtual void push(T *t){
    WaitForSingleObject(&mutex, INFINITE);
    list.push_back(t);
    ReleaseMutex(&mutex);
  }
  virtual T* pop(){
    T *t(0);
    WaitForSingleObject(&mutex, INFINITE);
    if (!list.empty()){
      t = list.front();
      list.pop_front();
    }
    ReleaseMutex(&mutex);
    return t;
  }
private:
  HANDLE mutex;
  Node<T> head;
  Node<T> tail;
  std::list<T *> list;
};

void consumer(void *_list){
  List<int> *list = (List<int> *)_list;
  bool last(false);
  int count(0);

  while (true){
    int *w = list->pop();
    if (w == 0){
      continue;
    }
    printf("-%d\n", *(int *)w); fflush(stdout);
    delete w;
  }
}

void producer(void *_list){
  int count(5);
  List<int> *list = (List<int> *)_list;
  for (int i = 0; i < count; ++i){
    int *w = new int(rand() % 100);
    list->push(w);
    printf("+%d\n", *w); fflush(stdout);
  }
}

int main(){
  int consumerNumber(3);
  int producerNumber(5);
  HANDLE *handles = (HANDLE *)malloc(sizeof(HANDLE) * (consumerNumber + producerNumber));
  int k = 0;
  
  List<int> *list = new List1<int>;
  for (int i = 0; i < producerNumber; ++i){
    handles[k++] = (HANDLE)_beginthread(producer, 0, list);
  }
  for (int i = 0; i < consumerNumber; ++i){
    handles[k++] = (HANDLE)_beginthread(consumer, 0, list);
  }

  WaitForMultipleObjects(producerNumber, handles, TRUE, INFINITE);
  Sleep(3000);
  return 0;
}

