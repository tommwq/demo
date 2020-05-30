#pragma once

#include "Instrument_move.hh"
#include <cmath>
#include <stdexcept>
#include <iostream>
#include "Field.hh"

namespace mix {
    void Instrument_move::execute(Machine& machine) const {
        std::uint32_t source = locate(machine);
        int field = get_field().to_unsigned();
        std::uint32_t destination;

        while (field-- > 0) {
            destination = static_cast<std::uint32_t>(machine.get_ri(1).to_long());
            machine.write_memory(destination, machine.read_memory(source));

            Word value(machine.get_ri(1).to_long() + 1);
            value.set_positive();
            value.set_byte(1, Byte(0));
            value.set_byte(2, Byte(0));
            value.set_byte(3, Byte(0));

            machine.get_ri(1) = value;
        }
    }
}
