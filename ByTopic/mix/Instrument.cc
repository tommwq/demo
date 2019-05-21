#include "Instrument.hh"

namespace mix {

    Word Instrument::get_address(const Word& encoded_instrument) {
        Byte high = encoded_instrument.get_byte(1);
        Byte low = encoded_instrument.get_byte(2);
        bool negative = encoded_instrument.is_negative();
        Word address;
        if (negative) {
            address.set_negative();
        }

        address.set_byte(4, high);
        address.set_byte(5, low);
        return address;
    }
}
