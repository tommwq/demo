#pragma once

#include "Instrument.hh"

namespace mix {
    class Instrument_enna: public Instrument {
    public:
        Instrument_enna(const Word& word): Instrument(word){}
        Instrument_enna(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };

    class Instrument_ennx: public Instrument {
    public:
        Instrument_ennx(const Word& word): Instrument(word){}
        Instrument_ennx(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };

    template<int Index>
    class Instrument_enn_: public Instrument {
        static_assert(1 <= Index && Index <= 6, "address register index must be in [1, 6]");
    public:
        Instrument_enn_(const Word& word): Instrument(word){}
        Instrument_enn_(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };
}

#include "Instrument_enn_.it"
