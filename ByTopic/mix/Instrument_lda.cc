#pragma once

#include "Instrument_lda.hh"
#include <stdexcept>
#include <iostream>
#include "Field.hh"

namespace mix {
    void Instrument_lda::execute(Machine& machine) {
        long base_address = 0;
        
        std::uint8_t index = get_address_register_index().to_unsigned();
        if (index > 0 && index < 6) {
            base_address = machine.get_ri(index).to_long();
        }

        long offset = get_address().to_long();
        long address = base_address + offset;
        if (address < 0) {
            throw std::runtime_error("invalid memory address");
        }
        
        Word value = machine.read_memory(static_cast<std::uint32_t>(address));
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

        machine.get_ra() = result;
    }
}
