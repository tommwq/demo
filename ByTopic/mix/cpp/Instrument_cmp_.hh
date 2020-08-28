#pragma once

#include "Instrument.hh"

namespace mix {
    class Instrument_cmpa: public Instrument {
    public:
        Instrument_cmpa(const Word& word): Instrument(word){}
        Instrument_cmpa(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };

    class Instrument_cmpx: public Instrument {
    public:
        Instrument_cmpx(const Word& word): Instrument(word){}
        Instrument_cmpx(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };

    template<int Index>
    class Instrument_cmp_: public Instrument {
        static_assert(1 <= Index && Index <= 6, "address register index must be in [1, 6]");
    public:
        Instrument_cmp_(const Word& word): Instrument(word){}
        Instrument_cmp_(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };
}

#include "Instrument_cmp_.it"
