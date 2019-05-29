#pragma once

#include "Instrument.hh"

namespace mix {

    class Instrument_num: public Instrument {
    public:
        Instrument_num(const Word& word): Instrument(word){}
        Instrument_num(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };

}


