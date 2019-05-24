#pragma once

#include "Instrument.hh"

namespace mix {
    class Instrument_enna: public Instrument {
    public:
        Instrument_enna(const Word& word): Instrument(word){}
        Instrument_enna(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };
}
