// cl main.cpp /std:c++17 /EHsc

#include <iostream>
#include <chrono>

using namespace std;
using namespace std::chrono;

class Next_on_free_list {
public:
    Next_on_free_list *next;
};

class Rational {
public:
    Rational(int a = 0, int b = 0)
        :n(a), d(b) {
    }
    inline void *operator new(size_t size);
    inline void operator delete(void *doomed, size_t size);
    static void new_memory_pool(){
        expand_the_free_list();
    }
    static void delete_memory_pool();
private:
    static Next_on_free_list *free_list;
    static void expand_the_free_list();
    enum {
        EXPANSION_SIZE = 32
    };
private:
    int n;
    int d;
};

inline void* Rational::operator new(size_t size) {
    if (free_list == nullptr) {
        expand_the_free_list();
    }

    Next_on_free_list *head = free_list;
    free_list = head->next;

    return head;
}

inline void Rational::operator delete(void *doomed, size_t size) {
    Next_on_free_list *head = static_cast<Next_on_free_list*>(doomed);
    head->next = free_list;
    free_list = head;
}

void Rational::expand_the_free_list() {
    size_t size = (sizeof(Rational) > sizeof(Next_on_free_list*)) ? sizeof(Rational) : sizeof(Next_on_free_list*);
    Next_on_free_list *runner = reinterpret_cast<Next_on_free_list*>(new char[size]);
    free_list = runner;
    for (int i = 0; i < EXPANSION_SIZE; i++) {
        runner->next = reinterpret_cast<Next_on_free_list*>(new char[size]);
        runner = runner->next;
    }

    runner->next = nullptr;
}

void Rational::delete_memory_pool() {
    Next_on_free_list *next_ptr;
    for (next_ptr = free_list; next_ptr != nullptr; next_ptr = free_list) {
        free_list = free_list->next;
        delete[] next_ptr;
    }
}

Next_on_free_list *Rational::free_list = nullptr;

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
