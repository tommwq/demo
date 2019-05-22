#pragma once

#include "Instrument.hh"

namespace mix {
    class Instrument_sub: public Instrument {
    public:
        Instrument_sub(const Word& word): Instrument(word){}
        Instrument_sub(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };
}
