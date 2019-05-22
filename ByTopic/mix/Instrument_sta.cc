#pragma once

#include "Instrument_sta.hh"
#include <stdexcept>
#include <iostream>
#include "Field.hh"

namespace mix {
    void Instrument_sta::execute(Machine& machine) const {
        store(machine, machine.get_ra());
    }
}
