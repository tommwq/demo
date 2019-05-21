#pragma once

#include "Instrument.hh"

namespace mix {
    class Instrument_lda: public Instrument {
    public:
        Instrument_lda(): Instrument(){}
        Instrument_lda(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) override;
    };
}
