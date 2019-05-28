#pragma once

#include "Instrument_src.hh"
#include <cmath>
#include <stdexcept>
#include <iostream>
#include "Field.hh"

namespace mix {
    void Instrument_src::execute(Machine& machine) const {
        int m = static_cast<int>(locate(machine));
        Word& ra = machine.get_ra();
        Word& rx = machine.get_rx();

        Byte tmp = ra.get_byte(1);

        for (int i = 1; i <= 9; i++) {
            if (i <= 5) {
                ra.set_byte(i, (i - m) > 0 ? ra.get_byte(i - m) : rx.get_byte(i - m + 5));
            } else {
                rx.set_byte(i, (i - m) > 5 ? rx.get_byte(i - m - 5) : ra.get_byte(i - m));
            }
        }

        rx.set_byte(5, tmp);
    }
}
