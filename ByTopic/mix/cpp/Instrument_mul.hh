#pragma once

#include "Instrument.hh"

namespace mix {
    class Instrument_mul: public Instrument {
    public:
        Instrument_mul(const Word& word): Instrument(word){}
        Instrument_mul(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };
}
