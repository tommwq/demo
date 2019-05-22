#pragma once

#include "Instrument_ld_.hh"
#include <stdexcept>
#include <iostream>
#include "Field.hh"

namespace mix {
    void Instrument_ld_::execute(Machine& machine) const {
        Word result = load(machine);
        if (Field::get_left(get_field().to_unsigned()) > 0) {
            result.set_positive();
        }
        
        result.get_byte(1).clear();
        result.get_byte(2).clear();
        result.get_byte(3).clear();

        machine.get_ri(address_register_index) = result;
    }
}
