#pragma once

#include "Instrument_enn_.hh"
#include <stdexcept>
#include <iostream>
#include "Field.hh"

namespace mix {
    void Instrument_enn_::execute(Machine& machine) const {
        Word value = locate(machine);
        if (Field::get_left(get_field().to_unsigned()) == 0) {
            value.flip_sign();
        }
        value.set_byte(1, Byte(0));
        value.set_byte(2, Byte(0));
        value.set_byte(3, Byte(0));
        machine.get_ri(address_register_index) = value;
    }
}
