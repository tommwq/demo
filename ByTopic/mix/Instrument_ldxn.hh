#pragma once

#include "Instrument.hh"

namespace mix {
    class Instrument_ldxn: public Instrument {
    public:
        Instrument_ldxn(const Word& word): Instrument(word){}
        Instrument_ldxn(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };
}
