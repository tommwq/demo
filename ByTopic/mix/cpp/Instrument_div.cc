#pragma once

#include "Instrument_div.hh"
#include <cmath>
#include <stdexcept>
#include <iostream>
#include "Field.hh"

namespace mix {
    void Instrument_div::execute(Machine& machine) const {

        Word value = load(machine);
        if (Field::get_left(get_field().to_unsigned()) > 0) {
            value.set_positive();
        }

        std::int64_t op2 = value.to_long();
        if (op2 == 0) {
            std::cout << "error" << std::endl;
            throw std::runtime_error("divide by zero");
        }
        std::int64_t op1 = (machine.get_ra().to_long() << 30) + std::abs(machine.get_rx().to_long());

        bool ra_positive = true;
        bool rx_positive = machine.get_ra().is_positive();

        if (op1 > 0 && op2 < 0) {
            op2 *= -1;
            ra_positive = false;
        } else if (op1 < 0 && op2 > 0) {
            op1 *= -1;
            ra_positive = false;
        }
        
        std::int64_t quotient = op1 / op2;
        std::int64_t remainder = op1 % op2;

        if (quotient > Byte::Max5) {
            machine.get_overflow_toggle().turn_on();
        }

        Word ra, rx;
        ra.assign(quotient);
        rx.assign(remainder);

        if (!ra_positive) {
            ra.set_negative();
        }

        if (!rx_positive) {
            rx.set_negative();
        }
        
        machine.get_ra() = ra;
        machine.get_rx() = rx;
    }
}
