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

    void Instrument_jred::execute(Machine& machine) const {
        if (machine.get_device(get_field().to_unsigned())->is_ready()) {
            Jump_helper::jump(machine, load(machine));
        }
    }

    void Instrument_jbus::execute(Machine& machine) const {
        if (machine.get_device(get_field().to_unsigned())->is_busy()) {
            Jump_helper::jump(machine, load(machine));
        }
    }
}
