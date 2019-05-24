#pragma once

#include "Instrument_ennx.hh"
#include <stdexcept>
#include <iostream>
#include "Field.hh"

namespace mix {
    void Instrument_ennx::execute(Machine& machine) const {
        Word value = locate(machine);
        if (Field::get_left(get_field().to_unsigned()) == 0) {
            value.flip_sign();
        }
        machine.get_rx() = value;
    }
}
