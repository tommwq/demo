#pragma once

#include "Instrument_dec_.hh"
#include <stdexcept>
#include <iostream>
#include "Field.hh"

namespace mix {
    void Instrument_dec_::execute(Machine& machine) const {
        Word& ri = machine.get_ri(address_register_index);
        ri.assign(ri.to_long() - 1);
    }
}
