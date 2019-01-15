/*

  Return Value Optimization

  /out:rvo.exe
  rvo.obj
  __thiscall Integer::Integer(int) 1 002FFD24
  __thiscall Integer::Integer(int) 2 002FFD28
  __thiscall Integer::Integer(int) 0 002FFD00
  __thiscall Integer::Integer(const class Integer &) 3 002FFD18
  __thiscall Integer::~Integer(void) 002FFD00
  __thiscall Integer::Integer(int) -1 002FFD1C
  __thiscall Integer::Integer(int) 2 002FFD00
  __thiscall Integer::Integer(const class Integer &) 2 002FFD20
  __thiscall Integer::~Integer(void) 002FFD00
  __thiscall Integer::~Integer(void) 002FFD20
  __thiscall Integer::~Integer(void) 002FFD1C
  __thiscall Integer::~Integer(void) 002FFD18
  __thiscall Integer::~Integer(void) 002FFD28
  __thiscall Integer::~Integer(void) 002FFD24

*/

#include <iostream>
#include <string>

class Integer {
    friend Integer Add(const Integer&, const Integer&);
    friend Integer Sub(const Integer&, const Integer&);
    friend Integer Mul(const Integer&, const Integer&);
public:
    Integer(int value = 0)
        :value(value) {
        std::cout << __FUNCSIG__ << " " << value << " " << this << std::endl;
    }

    Integer(const Integer& other)
        :value(other.value) {
        std::cout << __FUNCSIG__ << " " << value << " " << this << std::endl;
    }

    ~Integer() {
        std::cout << __FUNCSIG__ << " " << this << std::endl;
    }
    
private:
    int value;
};

Integer Add(const Integer &a, const Integer &b) {
    Integer result;
    result.value = a.value + b.value;
    return result;
}

Integer Sub(const Integer &a, const Integer &b) {
    return Integer(a.value - b.value);
}

Integer Mul(const Integer &a, const Integer &b) {
    Integer result(a.value * b.value);
    return result;
}

int main() {
    Integer a(1);
    Integer b(2);
    Integer c = Add(a, b);
    Integer d = Sub(a, b);
    Integer e = Mul(a, b);
}
