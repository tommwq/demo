#pragma once

#include "Instrument.hh"

namespace mix {
    class Instrument_deca: public Instrument {
    public:
        Instrument_deca(const Word& word): Instrument(word){}
        Instrument_deca(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };
}
