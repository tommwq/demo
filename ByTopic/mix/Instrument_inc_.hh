#pragma once

#include "Instrument.hh"

namespace mix {

    class Instrument_inca: public Instrument {
    public:
        Instrument_inca(const Word& word): Instrument(word){}
        Instrument_inca(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };

    class Instrument_incx: public Instrument {
    public:
        Instrument_incx(const Word& word): Instrument(word){}
        Instrument_incx(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };

    template<int Index> 
    class Instrument_inc_: public Instrument {
        static_assert(1 <= Index && Index <= 6, "address register index must be in [1, 6]");
    public:
        Instrument_inc_(const Word& word): Instrument(word){}
        Instrument_inc_(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };
}

#include "Instrument_inc_.it"
