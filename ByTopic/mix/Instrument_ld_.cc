#pragma once

#include "Instrument_ld_.hh"
#include <stdexcept>
#include <iostream>
#include "Field.hh"

namespace mix {
    void Instrument_ld_::execute(Machine& machine) const {

        Word value = machine.read_memory(locate(machine));

        Word result;
        std::uint8_t left = Field::get_left(get_field().to_unsigned());
        std::uint8_t right = Field::get_right(get_field().to_unsigned());

        if (left == 0 && value.is_negative()) {
            result.set_negative();
            left++;
        }

        for (std::uint8_t pos = right; pos >= left; pos--) {
            result.set_byte(5 + pos - right, value.get_byte(pos));
        }
        result.get_byte(1).clear();
        result.get_byte(2).clear();
        result.get_byte(3).clear();

        machine.get_ri(address_register_index) = result;
    }
}
