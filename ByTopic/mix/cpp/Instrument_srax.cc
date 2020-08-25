#pragma once

#include "Instrument_srax.hh"
#include <cmath>
#include <stdexcept>
#include <iostream>
#include "Field.hh"

namespace mix {

    void Instrument_srax::execute(Machine& machine) const {
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

        for (int i = 10; i >= 1; i--) {
            if (i > 5) {
                rx.set_byte(i - 5, (i - m > 5) ? rx.get_byte(i - m - 5) : ra.get_byte(i - m));
            } else {
                ra.set_byte(i, (i - m > 0) ? ra.get_byte(i - m) : 0);
            }
        }
    }
}
