#pragma once

#include "Instrument_num.hh"
#include <cmath>
#include <stdexcept>
#include <iostream>
#include "Field.hh"

namespace mix {
    void Instrument_num::execute(Machine& machine) const {
        const Word& ra = machine.get_ra();
        const Word& rx = machine.get_rx();

        std::uint32_t high = 0;
        std::uint32_t low = 0;

        for (int i = 5; i >= 1; i++) {
            high = high * 10 + ra.get_byte(i).to_unsigned();
            low = low * 10 + rx.get_byte(i).to_unsigned();
        }

        std::uint32_t value = high * 100000 + low;
        machine.get_ra().assign(value);
    }
}
