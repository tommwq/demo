#pragma once

#include "Instrument_sla.hh"
#include <cmath>
#include <stdexcept>
#include <iostream>
#include "Field.hh"

namespace mix {
    void Instrument_sla::execute(Machine& machine) const {
        int m = static_cast<int>(locate(machine));
        Word& value = machine.get_ra();

        if (m >= 5) {
            value.set_byte(1, Byte(0));
            value.set_byte(2, Byte(0));
            value.set_byte(3, Byte(0));
            value.set_byte(4, Byte(0));
            value.set_byte(5, Byte(0));
            return;
        }

        for (int i = 1; i <= 5; i++) {
            value.set_byte(i, (i + m <= 5) ? value.get_byte(i + m) : 0);
        }
    }
}
