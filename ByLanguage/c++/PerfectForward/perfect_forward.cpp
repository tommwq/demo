#include <iostream>
#include <utility>

#define PRINT_LINE() std::cout << __FUNCSIG__ << std::endl

template<typename T>
void second(T t) {
    PRINT_LINE();
}

template<typename T>
void first(T&& t) {
    PRINT_LINE();
        
    second(t); // 没有转发，&&丢失
    second(std::forward<T>(t));
}

class T {
public:
    T() {
        PRINT_LINE();
    }
    T(const T &o) {
        PRINT_LINE();
    }
    T(T &&o) {
        PRINT_LINE();
    }
};

int main() {

    std::cout << "named object" << std::endl;
    T t;
    first(t);  // 具名对象是左值，forward保持左右值性质，因此不会调用移动构造函数。

    std::cout << "unnamed object" << std::endl;
    first(T());
}
