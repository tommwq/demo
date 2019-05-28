#pragma once

#include "Instrument_dec_.hh"
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

    void Instrument_decx::execute(Machine& machine) const {
        
        Word& rx = machine.get_rx();
        if (rx.to_long() == -Byte::Max5) {
            machine.get_overflow_toggle().turn_on();
        }

        rx.assign(rx.to_long() - 1);
    }
}
