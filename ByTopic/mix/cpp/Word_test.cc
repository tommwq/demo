#include "Word.hh"
#include <iostream>

#include "unittest.hh"


int main() {
    Suit("Word assign value");
    
    mix::Word w1(64);
    Expect(0, w1.get_byte(5).get_value());
    Expect(1, w1.get_byte(4).get_value());
    ExpectTrue(w1.is_positive());

    mix::Word w2 = {0, 1, 2, 3, 4, 5};
    ExpectFalse(w2.is_positive());
    Expect(1, w2.get_byte(1).get_value());
    Expect(2, w2.get_byte(2).get_value());
    Expect(3, w2.get_byte(3).get_value());
    Expect(4, w2.get_byte(4).get_value());
    Expect(5, w2.get_byte(5).get_value());

    mix::Word w3;
    w3 = w2;
    ExpectFalse(w2.is_positive());
    Expect(1, w2.get_byte(1).get_value());
    Expect(2, w2.get_byte(2).get_value());
    Expect(3, w2.get_byte(3).get_value());
    Expect(4, w2.get_byte(4).get_value());
    Expect(5, w2.get_byte(5).get_value());

    mix::Word w4, w5;
    ExpectTrue(w4 == w5);
}
