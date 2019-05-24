#pragma once

#include "Instrument.hh"

namespace mix {
    class Instrument_decx: public Instrument {
    public:
        Instrument_decx(const Word& word): Instrument(word){}
        Instrument_decx(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };
}
