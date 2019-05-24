#pragma once

#include "Instrument.hh"

namespace mix {
    class Instrument_inca: public Instrument {
    public:
        Instrument_inca(const Word& word): Instrument(word){}
        Instrument_inca(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };
}
