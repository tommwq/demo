#pragma once

#include "Instrument_sra.hh"
#include <cmath>
#include <stdexcept>
#include <iostream>
#include "Field.hh"

namespace mix {
    void Instrument_sra::execute(Machine& machine) const {
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

        for (int i = 5; i > 0; i--) {
            value.set_byte(i, (i - m > 0) ? value.get_byte(i - m) : 0);
        }
    }
}
