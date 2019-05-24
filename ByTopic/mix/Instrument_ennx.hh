#pragma once

#include "Instrument.hh"

namespace mix {
    class Instrument_ennx: public Instrument {
    public:
        Instrument_ennx(const Word& word): Instrument(word){}
        Instrument_ennx(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };
}
