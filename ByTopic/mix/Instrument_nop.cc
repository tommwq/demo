#pragma once

#include "Instrument_nop.hh"
#include <cmath>
#include <stdexcept>
#include <iostream>
#include "Field.hh"

namespace mix {
    void Instrument_nop::execute(Machine& machine) const {
        return;
    }
}
