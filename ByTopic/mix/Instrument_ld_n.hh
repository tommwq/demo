#pragma once

#include "Instrument.hh"

namespace mix {
    class Instrument_ldxn: public Instrument {
    public:
        Instrument_ldxn(const Word& word): Instrument(word){}
        Instrument_ldxn(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };

    class Instrument_ldan: public Instrument {
    public:
        Instrument_ldan(const Word& word): Instrument(word){}
        Instrument_ldan(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };
    
    template<int Index>
    class Instrument_ld_n: public Instrument {
        static_assert(1 <= Index && Index <= 6, "address register index must be in [1, 6]");
    public:
        Instrument_ld_n(const Word& word): Instrument(word){}
        Instrument_ld_n(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };
}

#include "Instrument_ld_n.it"
