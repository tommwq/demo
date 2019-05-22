#pragma once

#include "Instrument.hh"

namespace mix {
    class Instrument_ldan: public Instrument {
    public:
        Instrument_ldan(const Word& word): Instrument(word){}
        Instrument_ldan(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };
}
