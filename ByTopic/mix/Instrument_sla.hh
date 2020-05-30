#pragma once

#include "Instrument.hh"

namespace mix {
    class Instrument_sla: public Instrument {
    public:
        Instrument_sla(const Word& word): Instrument(word){}
        Instrument_sla(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };
}
