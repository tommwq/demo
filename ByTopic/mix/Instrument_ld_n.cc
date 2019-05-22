#pragma once

#include "Instrument_ld_n.hh"
#include <stdexcept>
#include <iostream>
#include "Field.hh"

namespace mix {
    void Instrument_ld_n::execute(Machine& machine) const {
        Word result = load(machine);
        if (Field::get_left(get_field().to_unsigned()) == 0) {
            result.flip_sign();
        } else {
            result.set_positive();
        }
        
        result.get_byte(1).clear();
        result.get_byte(2).clear();
        result.get_byte(3).clear();

        machine.get_ri(address_register_index) = result;
    }
}
