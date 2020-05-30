#pragma once

#include "Instrument_cmp_.hh"
#include <stdexcept>
#include <iostream>
#include "Field.hh"

namespace mix {
    
    void Instrument_cmpa::execute(Machine& machine) const {
        long op1 = machine.get_ra().to_long(get_field().to_unsigned());
        long op2 = load(machine).to_long();

        if (op1 == op2) {
            machine.get_compare_indicator().turn_equal();
        } else if (op1 > op2) {
            machine.get_compare_indicator().turn_greater();
        } else {
            machine.get_compare_indicator().turn_less();
        }
    }

    void Instrument_cmpx::execute(Machine& machine) const {
        long op1 = machine.get_rx().to_long(get_field().to_unsigned());
        long op2 = load(machine).to_long();

        if (op1 == op2) {
            machine.get_compare_indicator().turn_equal();
        } else if (op1 > op2) {
            machine.get_compare_indicator().turn_greater();
        } else {
            machine.get_compare_indicator().turn_less();
        }
    }
}
