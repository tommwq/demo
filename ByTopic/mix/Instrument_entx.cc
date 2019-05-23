#pragma once

#include "Instrument_entx.hh"
#include <stdexcept>
#include <iostream>
#include "Field.hh"

namespace mix {
    void Instrument_entx::execute(Machine& machine) const {
        machine.get_rx().assign(locate(machine));
    }
}
