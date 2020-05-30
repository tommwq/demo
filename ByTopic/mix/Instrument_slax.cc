#pragma once

#include "Instrument_slax.hh"
#include <cmath>
#include <stdexcept>
#include <iostream>
#include "Field.hh"

namespace mix {

    void Instrument_slax::execute(Machine& machine) const {
        int m = static_cast<int>(locate(machine));
        Word& ra = machine.get_ra();
        Word& rx = machine.get_rx();

        if (m >= 10) {
            ra.set_byte(1, Byte(0));
            ra.set_byte(2, Byte(0));
            ra.set_byte(3, Byte(0));
            ra.set_byte(4, Byte(0));
            ra.set_byte(5, Byte(0));
            rx.set_byte(1, Byte(0));
            rx.set_byte(2, Byte(0));
            rx.set_byte(3, Byte(0));
            rx.set_byte(4, Byte(0));
            rx.set_byte(5, Byte(0));
            return;
        }

        for (int i = 1; i <= 10; i++) {
            if (i <= 5) {
                ra.set_byte(i, (i + m <= 5) ? ra.get_byte(i + m) : rx.get_byte(i + m - 5));
            } else {
                rx.set_byte(i - 5, (i + m <= 10) ? rx.get_byte(i + m - 5) : 0);
            }
        }
    }
}
