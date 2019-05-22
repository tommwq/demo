#pragma once

#include "Instrument.hh"

namespace mix {
    class Instrument_stz: public Instrument {
    public:
        Instrument_stz(const Word& word): Instrument(word){}
        Instrument_stz(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };
}
