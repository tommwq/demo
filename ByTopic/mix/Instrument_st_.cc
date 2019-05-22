#pragma once

#include "Instrument_st_.hh"
#include <stdexcept>
#include <iostream>
#include "Field.hh"

namespace mix {
    void Instrument_st_::execute(Machine& machine) const {
        store(machine, machine.get_ri(get_index().to_unsigned()));
    }
}
