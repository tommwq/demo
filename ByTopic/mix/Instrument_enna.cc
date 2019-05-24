#pragma once

#include "Instrument_enna.hh"
#include <stdexcept>
#include <iostream>
#include "Field.hh"

namespace mix {
    void Instrument_enna::execute(Machine& machine) const {
        Word value = locate(machine);
        if (Field::get_left(get_field().to_unsigned()) == 0) {
            value.flip_sign();
        }
        machine.get_ra() = value;
    }
}
