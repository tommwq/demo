#pragma once

#include "Instrument_ldx.hh"
#include <stdexcept>
#include <iostream>
#include "Field.hh"

namespace mix {
    void Instrument_ldx::execute(Machine& machine) const {
        
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

        machine.get_rx() = result;
    }
}
