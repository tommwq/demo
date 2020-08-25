#pragma once

#include "Instrument.hh"

namespace mix {

    class Instrument_nop: public Instrument {
    public:
        Instrument_nop(const Word& word): Instrument(word){}
        Instrument_nop(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };

}


