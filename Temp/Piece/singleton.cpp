
#include <cstdlib>
#include <iostream>

template<class T>
class Singleton {
public:
  static T& instance();
private:
  Singleton();
  Singleton(const Singleton &);
  ~Singleton();
  Singleton& operator=(const Singleton &);
};
template<class T>
T& Singleton<T>::instance(){
  static T object;
  return object;
}
////////////////////////////////////////////////////////////////////////////////////////
class Foo {
public:
  Foo();
  ~Foo();
};
Foo::Foo(){
  std::cout << __PRETTY_FUNCTION__ << std::endl;
}
Foo::~Foo(){
  std::cout << __PRETTY_FUNCTION__ << std::endl;
}
////////////////////////////////////////////////////////////////////////////////////////
int main(void){
  Foo &foo = Singleton<Foo>::instance();
  return 0;
}
