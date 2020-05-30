#pragma once

#include "Instrument.hh"

namespace mix {

    class Instrument_move: public Instrument {
    public:
        Instrument_move(const Word& word): Instrument(word){}
        Instrument_move(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };

}


