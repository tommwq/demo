#pragma once

#include "Instrument.hh"

namespace mix {
    class Instrument_incx: public Instrument {
    public:
        Instrument_incx(const Word& word): Instrument(word){}
        Instrument_incx(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };
}
