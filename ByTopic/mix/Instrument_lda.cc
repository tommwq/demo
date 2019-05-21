#pragma once

#include "Instrument_lda.hh"
#include <stdexcept>

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

        machine.get_ra() = value;
    }
}
