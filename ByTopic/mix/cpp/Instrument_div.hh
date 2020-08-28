#pragma once

#include "Instrument.hh"

namespace mix {
    class Instrument_div: public Instrument {
    public:
        Instrument_div(const Word& word): Instrument(word){}
        Instrument_div(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };
}
