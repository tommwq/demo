#pragma once

#include "Instrument.hh"

namespace mix {
    class Instrument_src: public Instrument {
    public:
        Instrument_src(const Word& word): Instrument(word){}
        Instrument_src(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };
}
