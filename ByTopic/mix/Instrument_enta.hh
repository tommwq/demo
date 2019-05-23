#pragma once

#include "Instrument.hh"

namespace mix {
    class Instrument_enta: public Instrument {
    public:
        Instrument_enta(const Word& word): Instrument(word){}
        Instrument_enta(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };
}
