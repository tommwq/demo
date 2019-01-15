// cl main.cpp /std:c++17 /EHsc

#include <iostream>
#include <chrono>
#include <mutex>

#define CHECK() cout << __FILE__ << ":" << __LINE__ << endl

using namespace std;
using namespace std::chrono;

class Memory_chunk {
public:
    Memory_chunk(Memory_chunk *next_chunk, size_t chunk_size);
    ~Memory_chunk() { delete[] memory; }
    inline void* alloc(size_t size);
    inline void free(void *ptr);
    Memory_chunk* get_next_chunk() { return next_chunk; }
    size_t space_available() { return chunk_size - allocated; }
    enum { DEFAULT_CHUNK_SIZE = 4096 };
private:
    Memory_chunk *next_chunk;
    char *memory;
    size_t chunk_size;
    size_t allocated;
};

Memory_chunk::Memory_chunk(Memory_chunk *next_chunk, size_t size) {
    chunk_size = (size > DEFAULT_CHUNK_SIZE) ? size : DEFAULT_CHUNK_SIZE;
    this->next_chunk = next_chunk;
    allocated = 0;
    memory = new char[chunk_size];
}

inline void Memory_chunk::free(void *ptr) {
    // do nothing
}

inline void* Memory_chunk::alloc(size_t size) {
    void *addr = memory + allocated;
    allocated += size;
    return addr;
}

class Byte_memory_pool {
public:
    Byte_memory_pool(size_t init_size = Memory_chunk::DEFAULT_CHUNK_SIZE);
    ~Byte_memory_pool();

    inline void* alloc(size_t size);
    inline void free(void *ptr);
private:
    Memory_chunk *chunk_list;
    void expand_storage(size_t size);
};

Byte_memory_pool::Byte_memory_pool(size_t init_size) {
    expand_storage(init_size);
}

Byte_memory_pool::~Byte_memory_pool() {
    Memory_chunk *chunk = chunk_list;
    while (chunk != nullptr) {
        chunk_list = chunk->get_next_chunk();
        delete[] reinterpret_cast<char*>(chunk);
        chunk = chunk_list;
    }
}

inline void* Byte_memory_pool::alloc(size_t size) {
    size_t space = chunk_list->space_available();
    if (space < size) {
        expand_storage(size);
    }

    return chunk_list->alloc(size);
}

inline void Byte_memory_pool::free(void *ptr) {
    chunk_list->free(ptr);
}

void Byte_memory_pool::expand_storage(size_t size) {
    chunk_list = new Memory_chunk(chunk_list, size);
}

template<typename Memory_pool, typename Lock>
class Concurrency_memory_pool {
public:
    inline void* alloc(size_t size0);
    inline void free(void *ptr);
private:
    Memory_pool memory_pool;
    Lock lock;
};

template<typename Memory_pool, typename Lock>
inline void* Concurrency_memory_pool<Memory_pool, Lock>::alloc(size_t size) {
    void *memory;
    lock.lock();
    memory = memory_pool.alloc(size);
    lock.unlock();
    return memory;
}

template<typename Memory_pool, typename Lock>
inline void Concurrency_memory_pool<Memory_pool, Lock>::free(void *ptr) {
    lock.lock();
    memory_pool.free(ptr);
    lock.unlock();
}

class Lock {
public:
    virtual ~Lock(){}
    virtual void lock() = 0;
    virtual void unlock() = 0;
};

class MutexLock: public Lock {
public:
    void lock() {
        mtx.lock();
    }
    
    void unlock() {
        mtx.unlock();
    }
private:
    mutex mtx;
};

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
        memory_pool = new Concurrency_memory_pool<Byte_memory_pool, MutexLock>();
    }
    static void delete_memory_pool() {
        delete memory_pool;
        memory_pool = nullptr;
    }
private:
    enum {
        EXPANSION_SIZE = 32
    };
    static Concurrency_memory_pool<Byte_memory_pool, MutexLock> *memory_pool;
private:
    int n;
    int d;
};

Concurrency_memory_pool<Byte_memory_pool, MutexLock>* Rational::memory_pool;

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
