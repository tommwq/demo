/**
 * rvo.cpp
 * 测试g++的返回值优化。
 */

#include <ctime>
#include <iostream>

class Complex {
public:
  Complex(double real = 0.0, double image = 0.0):real_(real), image_(image){}
  Complex(const Complex &rhs):real_(rhs.real_), image_(rhs.image_){}
  Complex& operator=(const Complex &rhs){ real_ = rhs.real_; image_ = rhs.image_; return *this; }
  ~Complex(){}
  // rvo
  Complex operator+(const Complex &rhs){
    return Complex(real_ + rhs.real_, image_ + rhs.image_);
  }

  // no rvo
  Complex operator-(const Complex &rhs){
    Complex complex;
    complex.real_ = real_ - rhs.real_;
    complex.image_ = image_ = rhs.image_;
    return complex;
  }

private:
  double real_;
  double image_;
};

int main(){

  int i = 0;
  Complex a, b, c;
  clock_t start, stop;
  int count(1000 * 1000 * 20);

  start = std::clock();
  for (i = 0; i < count; ++i){
    c = a + b;
  }
  stop = std::clock();

  double elapsed_milliseconds(1.0 * (stop - start) / CLOCKS_PER_SEC * 1000);
  std::cout << "rvo: " << elapsed_milliseconds << "ms" << std::endl;

  start = std::clock();
  for (i = 0; i < count; ++i){
    c = a - b;
  }
  stop = std::clock();

  elapsed_milliseconds = 1.0 * (stop - start) / CLOCKS_PER_SEC * 1000;
  std::cout << "non rvo: " << elapsed_milliseconds << "ms" << std::endl;

  return 0;
}
