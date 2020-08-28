/*
  ¶¨Ê±Æ÷Àý×Ó¡£
  2013-06-15
*/

#undef NDEBUG
#include <cassert>
#include <cstdint>
#include <cstdlib>
#include <ctime>
#include <iostream>
#include <windows.h>

template<uint32_t millisecond>
class TimeSlicer {
public:
  TimeSlicer<millisecond>()
    :_millisecond(millisecond){
    DWORD now = GetTickCount();
    _last = now;
  };
  virtual ~TimeSlicer<millisecond>(){}
public:
  void next(){
    DWORD now = GetTickCount();
    uint32_t elapsed = now - _last;

    uint32_t add;
    if (_millisecond == elapsed){
      add = _millisecond;
    } else if (_millisecond < elapsed){
      add = (elapsed / _millisecond) * _millisecond;
    } else {
      Sleep(_millisecond - elapsed);
      add = _millisecond;
    }

    _last += add;

    uint32_t last = _last / 1000;
    assert(last != _tmp);
    std::cout << "last: " << _last / 1000 
              << " add: " << add << std::endl;
    //std::cout << "next: " << GetTickCount() / 1000 << std::endl;
  }
private:
  uint32_t _millisecond;
  uint32_t _last;
  uint32_t _tmp;
};

void logic(){
  Sleep(rand() % 2000);
}

int main(){
  srand(time(0));

  TimeSlicer<1000> timeSlicer;
  while (true){
    timeSlicer.next();
    logic();
  }
}
