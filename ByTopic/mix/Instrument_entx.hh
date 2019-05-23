#pragma once

#include "Instrument.hh"

namespace mix {
    class Instrument_entx: public Instrument {
    public:
        Instrument_entx(const Word& word): Instrument(word){}
        Instrument_entx(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };
}
