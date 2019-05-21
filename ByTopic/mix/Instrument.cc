#include "Instrument.hh"

namespace mix {

    Word Instrument::get_address() {
        Byte high = get_byte(1);
        Byte low = get_byte(2);
        bool negative = is_negative();
        
        Word address;
        if (negative) {
            address.set_negative();
        }

        address.set_byte(4, high);
        address.set_byte(5, low);
        return address;
    }
}
