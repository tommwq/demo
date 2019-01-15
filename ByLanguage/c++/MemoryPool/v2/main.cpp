// cl main.cpp /std:c++17 /EHsc

#include <iostream>
#include <chrono>

#define CHECK() cout << __FILE__ << ":" << __LINE__ << endl

using namespace std;
using namespace std::chrono;

template<typename T>
class Memory_pool {
public:
    Memory_pool(size_t size = EXPANSION_SIZE);
    ~Memory_pool();

    inline void* alloc(size_t size);
    inline void free(void *ptr);
private:
    Memory_pool<T> *next;
    enum { EXPANSION_SIZE = 32 };
    void expand_the_free_list(int count = EXPANSION_SIZE);
};

template<typename T>
Memory_pool<T>::Memory_pool(size_t size)
    :next(nullptr) {
    expand_the_free_list(size);
}

template<typename T>
Memory_pool<T>::~Memory_pool() {
    
    for (char *ptr = reinterpret_cast<char*>(next);
         ptr != nullptr;
         ptr = reinterpret_cast<char*>(next)) {
        next = next->next;
        delete[] ptr;
    }
    next = nullptr;
    
}

template<typename T>
inline void* Memory_pool<T>::alloc(size_t) {
    if (next == nullptr) {
        expand_the_free_list();
    }

    Memory_pool<T> *head = next;
    next = head->next;
    return head;
}

template<typename T>
inline void Memory_pool<T>::free(void *doomed) {
    Memory_pool<T> *head = reinterpret_cast<Memory_pool<T>*>(doomed);
    head->next = next;
    next = head;
}

template<typename T>
void Memory_pool<T>::expand_the_free_list(int count) {
    size_t size = (sizeof(T) > sizeof(Memory_pool<T>*)) ? sizeof(T) : sizeof(Memory_pool<T>*);

    Memory_pool<T> *runner = reinterpret_cast<Memory_pool<T>*>(new char[size]);
    next = runner;
    for (int i = 0; i < count; i++) {
        runner->next = reinterpret_cast<Memory_pool<T>*>(new char[size]);
        runner = runner->next;
    }

    runner->next = nullptr;
}

class Rational {
public:
    Rational(int a = 0, int b = 0)
        :n(a), d(b) {
    }
    inline void *operator new(size_t size) {
        return memory_pool->alloc(size);
    }
    inline void operator delete(void *doomed, size_t size) {
        return memory_pool->free(doomed);
    }
    static void new_memory_pool(){
        memory_pool = new Memory_pool<Rational>();
    }
    static void delete_memory_pool() {
        delete memory_pool;
        memory_pool = nullptr;
    }
private:
    enum {
        EXPANSION_SIZE = 32
    };
    static Memory_pool<Rational> *memory_pool;
private:
    int n;
    int d;
};

Memory_pool<Rational>* Rational::memory_pool;

int main() {
    Rational *array[1000];

    time_point<steady_clock> start_time;
    time_point<steady_clock> stop_time;
    microseconds duration;

    Rational::new_memory_pool();
    
    start_time = steady_clock::now();
    for (int j = 0; j < 500; j++) {
        for (int i = 0; i < 1000; i++) {
            array[i] = new Rational(i);
        }
        for (int i = 0; i < 1000; i++) {
            delete array[i];
        }
    }
    stop_time = steady_clock::now();

    Rational::delete_memory_pool();
    duration = duration_cast<microseconds>(stop_time - start_time);

    cout << duration.count() << " us " << endl;
}
