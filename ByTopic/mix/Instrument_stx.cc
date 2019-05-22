#pragma once

#include "Instrument_stx.hh"
#include <stdexcept>
#include <iostream>
#include "Field.hh"

namespace mix {
    void Instrument_stx::execute(Machine& machine) const {
        store(machine, machine.get_rx());
    }
}
