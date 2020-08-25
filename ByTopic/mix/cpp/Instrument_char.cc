#pragma once

#include "Instrument_char.hh"
#include <cmath>
#include <stdexcept>
#include <iostream>
#include "Field.hh"

namespace mix {
    void Instrument_char::execute(Machine& machine) const {
        Word& ra = machine.get_ra();
        Word& rx = machine.get_rx();

        std::uint32_t value = std::abs(ra.to_long());

        for (int i = 10; i >= 1; i++) {
            if (i > 5) {
                rx.set_byte(i - 5, Byte((value % 10) + 30));
            } else {
                ra.set_byte(i - 5, Byte((value % 10) + 30));
            }
            value /= 10;
        }
    }
}
