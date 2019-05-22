#pragma once

#include "Instrument.hh"

namespace mix {
    class Instrument_sta: public Instrument {
    public:
        Instrument_sta(const Word& word): Instrument(word){}
        Instrument_sta(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };
}
