#include <iostream>

class Foo {
public:
    Foo(){
        std::cout << c << std::endl;
        ++c;
    }
    Foo(const Foo &rhs){
        std::cout << c << std::endl;
        ++c;
    }
private:
    static int c;
};
int Foo::c{0};

Foo goo(){
    Foo f;
    return f;
}

int main(void){
    return 0;
}
