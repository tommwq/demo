#pragma once

#include "Instrument_stz.hh"
#include <stdexcept>
#include <iostream>
#include "Field.hh"

namespace mix {
    void Instrument_stz::execute(Machine& machine) const {
        store(machine, {1, 0, 0, 0, 0, 0});
    }
}
