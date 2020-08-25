#pragma once

#include "Instrument_st_.hh"
#include <stdexcept>
#include <iostream>
#include "Field.hh"

namespace mix {

    void Instrument_sta::execute(Machine& machine) const {
        store(machine, machine.get_ra());
    }

    void Instrument_stj::execute(Machine& machine) const {
        store(machine, machine.get_rj());
    }

    void Instrument_stx::execute(Machine& machine) const {
        store(machine, machine.get_rx());
    }

    void Instrument_stz::execute(Machine& machine) const {
        store(machine, {1, 0, 0, 0, 0, 0});
    }
}
