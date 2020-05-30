#pragma once

#include "Instrument.hh"

namespace mix {

    class Instrument_lda: public Instrument {
    public:
        Instrument_lda(const Word& word): Instrument(word){}
        Instrument_lda(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };

    class Instrument_ldx: public Instrument {
    public:
        Instrument_ldx(const Word& word): Instrument(word){}
        Instrument_ldx(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };

    template<int Index>
    class Instrument_ld_: public Instrument {
        static_assert(1 <= Index && Index <= 6, "address register index must be in [1, 6]");
    public:
        Instrument_ld_(const Word& word): Instrument(word){}
        Instrument_ld_(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };
}

#include "Instrument_ld_.it"
