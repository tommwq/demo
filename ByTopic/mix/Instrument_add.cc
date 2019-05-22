#pragma once

#include "Instrument_add.hh"
#include <stdexcept>
#include <iostream>
#include "Field.hh"

namespace mix {
    void Instrument_add::execute(Machine& machine) const {

        Word value = load(machine);
        if (Field::get_left(get_field().to_unsigned()) > 0) {
            value.set_positive();
        }

        long op1 = machine.get_ra().to_long();
        long op2 = value.to_long();
        long result = op1 + op2;
        if (result > Byte::Max5 || result < Byte::Max5) {
            machine.get_overflow_toggle().turn_on();
        }

        machine.get_ra().assign(static_cast<std::uint32_t>(result));
    }
}
