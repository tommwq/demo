#pragma once

#include "Instrument.hh"

namespace mix {
    class Instrument_ldx: public Instrument {
    public:
        Instrument_ldx(const Word& word): Instrument(word){}
        Instrument_ldx(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };
}
