#pragma once

#include "Instrument.hh"

namespace mix {
    class Instrument_srax: public Instrument {
    public:
        Instrument_srax(const Word& word): Instrument(word){}
        Instrument_srax(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };
}
