#pragma once

#include "Instrument_ld_n.hh"
#include <stdexcept>
#include <iostream>
#include "Field.hh"

namespace mix {

    void Instrument_ldxn::execute(Machine& machine) const {

        Word result = load(machine);
        if (Field::get_left(get_field().to_unsigned()) == 0) {
            result.flip_sign();
        } else {
            result.set_positive();
        }

        machine.get_rx() = result;
    }

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
