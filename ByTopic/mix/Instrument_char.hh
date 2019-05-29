#pragma once

#include "Instrument.hh"

namespace mix {

    class Instrument_char: public Instrument {
    public:
        Instrument_char(const Word& word): Instrument(word){}
        Instrument_char(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };

}


