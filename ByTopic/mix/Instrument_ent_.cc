#pragma once

#include "Instrument_ent_.hh"
#include <stdexcept>
#include <iostream>
#include "Field.hh"

namespace mix {

    void Instrument_entx::execute(Machine& machine) const {
        machine.get_rx().assign(locate(machine));
    }

    void Instrument_enta::execute(Machine& machine) const {
        machine.get_ra().assign(locate(machine));
    }
}
