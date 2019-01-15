/*

  /out:temporary_object.exe
  temporary_object.obj
  __thiscall Integer::Integer(int)
  __thiscall Integer::Integer(int)
  class Integer &__thiscall Integer::operator =(const class Integer &)
  void __thiscall Integer::assign(int)

*/

#include <iostream>
#include <string>

#define PRINT_FUNCTION() std::cout << __FUNCSIG__ << std::endl

class Integer {
public:
    Integer(int value = 0)
        :value(value){
        PRINT_FUNCTION();
    }

    Integer(const Integer &other)
        :value(other.value) {
        PRINT_FUNCTION();
    }

    Integer& operator=(const Integer& other) {
        PRINT_FUNCTION();
        value = other.value;
        return *this;
    }

    void assign(int value) {
        PRINT_FUNCTION();
        this->value = value;
    }

private:
    int value;
};



int main() {
    Integer i;
    i = 1;

    i.assign(2);
}
