#pragma once

#include "Instrument_enta.hh"
#include <stdexcept>
#include <iostream>
#include "Field.hh"

namespace mix {
    void Instrument_enta::execute(Machine& machine) const {
        machine.get_ra().assign(locate(machine));
    }
}
