#pragma once

#include "Instrument.hh"

namespace mix {
    class Instrument_sra: public Instrument {
    public:
        Instrument_sra(const Word& word): Instrument(word){}
        Instrument_sra(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };
}
