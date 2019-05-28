#pragma once

#include "Instrument.hh"

namespace mix {

    class Instrument_hlt: public Instrument {
    public:
        Instrument_hlt(const Word& word): Instrument(word){}
        Instrument_hlt(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };

}


