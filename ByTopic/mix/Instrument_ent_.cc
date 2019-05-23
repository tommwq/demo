#pragma once

#include "Instrument_ent_.hh"
#include <stdexcept>
#include <iostream>
#include "Field.hh"

namespace mix {
    void Instrument_ent_::execute(Machine& machine) const {
        machine.get_ri(address_register_index).assign(locate(machine));
    }
}
