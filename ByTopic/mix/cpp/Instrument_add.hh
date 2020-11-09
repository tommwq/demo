#pragma once

#include "Instrument.hh"

namespace mix {
    class Instrument_add: public Instrument {
    public:
        Instrument_add(const Word& word): Instrument(word){}
        Instrument_add(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };
}
