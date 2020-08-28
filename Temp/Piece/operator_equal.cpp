
#include <iostream>

class Foo {
public:
  Foo(){}
  Foo(const Foo &foo){
    std::cout << __PRETTY_FUNCTION__ << std::endl;
    *this = foo;
  }
  Foo operator= (const Foo &foo){
    std::cout << __PRETTY_FUNCTION__ << std::endl;
    return *this;
  }
};

class Bar {
public:
  Bar(){}
  Bar(const Bar &bar){
    std::cout << __PRETTY_FUNCTION__ << std::endl;
    *this = bar;
  }
  Bar& operator= (const Bar &bar){
    std::cout << __PRETTY_FUNCTION__ << std::endl;
    return *this;
  }
};


int main(){

  { // ok
    Bar a;
    Bar b(a);
  }

  { // infinite loop
    Foo a;
    Foo b(a);
  }

  return 0;
}
