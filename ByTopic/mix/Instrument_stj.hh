#pragma once

#include "Instrument.hh"

namespace mix {
    class Instrument_stj: public Instrument {
    public:
        Instrument_stj(const Word& word): Instrument(word){}
        Instrument_stj(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };
}
