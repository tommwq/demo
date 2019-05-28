#pragma once

#include "Instrument.hh"

namespace mix {

    class Instrument_enta: public Instrument {
    public:
        Instrument_enta(const Word& word): Instrument(word){}
        Instrument_enta(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };

    class Instrument_entx: public Instrument {
    public:
        Instrument_entx(const Word& word): Instrument(word){}
        Instrument_entx(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };

    template<int Index>
    class Instrument_ent_: public Instrument {
        static_assert(1 <= Index && Index <= 6, "address register index must be in [1, 6]");
    public:
        Instrument_ent_(const Word& word): Instrument(word){}
        Instrument_ent_(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };
}

#include "Instrument_ent_.it"
