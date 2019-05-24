#pragma once

#include "Instrument_inc_.hh"
#include <stdexcept>
#include <iostream>
#include "Field.hh"

namespace mix {
    void Instrument_inc_::execute(Machine& machine) const {
        Word& ri = machine.get_ri(address_register_index);
        ri.assign(ri.to_long() + 1);
    }
}
