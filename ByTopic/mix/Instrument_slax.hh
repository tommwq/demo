#pragma once

#include "Instrument.hh"

namespace mix {
    class Instrument_slax: public Instrument {
    public:
        Instrument_slax(const Word& word): Instrument(word){}
        Instrument_slax(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };
}
