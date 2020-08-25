#pragma once

#include "Instrument_ld_.hh"
#include <stdexcept>
#include <iostream>
#include "Field.hh"

namespace mix {
    void Instrument_ldx::execute(Machine& machine) const {
        
        Word result = load(machine);
        if (Field::get_left(get_field().to_unsigned()) > 0) {
            result.set_positive();
        }

        machine.get_rx() = result;
    }

    void Instrument_lda::execute(Machine& machine) const {

        Word result = load(machine);
        if (Field::get_left(get_field().to_unsigned()) > 0) {
            result.set_positive();
        }

        machine.get_ra() = result;
    }
}
