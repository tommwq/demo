#pragma once

#include "Instrument_stj.hh"
#include <stdexcept>
#include <iostream>
#include "Field.hh"

namespace mix {
    void Instrument_stj::execute(Machine& machine) const {
        store(machine, machine.get_rj());
    }
}
