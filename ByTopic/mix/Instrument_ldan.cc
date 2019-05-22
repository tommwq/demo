#pragma once

#include "Instrument_ldan.hh"
#include <stdexcept>
#include <iostream>
#include "Field.hh"

namespace mix {
    void Instrument_ldan::execute(Machine& machine) const {

        Word result = load(machine);
        if (Field::get_left(get_field().to_unsigned()) == 0) {
            result.flip_sign();
        } else {
            result.set_positive();
        }

        machine.get_ra() = result;
    }
}
