#pragma once

#include "Instrument_jmp_.hh"
#include <stdexcept>
#include <iostream>
#include "Field.hh"

namespace mix {
    
    void Instrument_jsj::execute(Machine& machine) const {
        Word aligned = Jump_helper::align_address(load(machine));
        machine.get_program_counter() = static_cast<std::uint32_t>(aligned.to_long());
    }
}
