#pragma once

#include "Instrument.hh"

namespace mix {
    class Instrument_stx: public Instrument {
    public:
        Instrument_stx(const Word& word): Instrument(word){}
        Instrument_stx(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };
}
