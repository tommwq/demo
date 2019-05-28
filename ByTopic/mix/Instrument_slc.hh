#pragma once

#include "Instrument.hh"

namespace mix {
    class Instrument_slc: public Instrument {
    public:
        Instrument_slc(const Word& word): Instrument(word){}
        Instrument_slc(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };
}
