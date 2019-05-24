#pragma once

#include "Instrument_deca.hh"
#include <stdexcept>
#include <iostream>
#include "Field.hh"

namespace mix {
    void Instrument_deca::execute(Machine& machine) const {

        Word& ra = machine.get_ra();
        if (ra.to_long() == -Byte::Max5) {
            machine.get_overflow_toggle().turn_on();
        }

        ra.assign(ra.to_long() - 1);
    }
}
