#pragma once

#include "Instrument.hh"

namespace mix {
    class Instrument_deca: public Instrument {
    public:
        Instrument_deca(const Word& word): Instrument(word){}
        Instrument_deca(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };

    class Instrument_decx: public Instrument {
    public:
        Instrument_decx(const Word& word): Instrument(word){}
        Instrument_decx(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };

    template<int Index>
    class Instrument_dec_: public Instrument {
        static_assert(1 <= Index && Index <= 6, "address register index must be in [1, 6]");
    public:
        Instrument_dec_(const Word& word): Instrument(word){}
        Instrument_dec_(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };
}

#include "Instrument_dec_.it"
