#include "Byte.hh"
#include <iostream>

#include "unittest.hh"


int main() {
    Suit("Byte assign value");
    
    mix::Byte b1(64);
    Expect(0, b1.get_value());

    mix::Byte b2(1);
    Expect(1, b2.get_value());

    mix::Byte b3(65);
    Expect(1, b3.get_value());
}
