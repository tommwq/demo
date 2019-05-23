#pragma once

#include "Instrument_mul.hh"
#include <stdexcept>
#include <iostream>
#include "Field.hh"

namespace mix {
    void Instrument_mul::execute(Machine& machine) const {

        Word value = load(machine);
        if (Field::get_left(get_field().to_unsigned()) > 0) {
            value.set_positive();
        }

        std::int64_t op1 = machine.get_ra().to_long();
        std::int64_t op2 = value.to_long();
        std::int64_t result = op1 * op2;

        Word ra, rx;
        bool is_positive = true;
        if (result < 0) {
            is_positive = false;
            result *= -1;
        }
        
        if (result > Word::Max2) {
            machine.get_overflow_toggle().turn_on();
        }

        ra.assign(result / (Byte::Max5 + 1));
        rx.assign(result % (Byte::Max5 + 1));
        if (!is_positive) {
            ra.set_negative();
            rx.set_negative();
        }
        
        machine.get_ra() = ra;
        machine.get_rx() = rx;
    }
}
