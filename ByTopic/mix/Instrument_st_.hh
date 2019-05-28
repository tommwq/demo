#pragma once

#include "Instrument.hh"

namespace mix {

    class Instrument_sta: public Instrument {
    public:
        Instrument_sta(const Word& word): Instrument(word){}
        Instrument_sta(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };

    class Instrument_stj: public Instrument {
    public:
        Instrument_stj(const Word& word): Instrument(word){}
        Instrument_stj(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };

    class Instrument_stx: public Instrument {
    public:
        Instrument_stx(const Word& word): Instrument(word){}
        Instrument_stx(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };

    class Instrument_stz: public Instrument {
    public:
        Instrument_stz(const Word& word): Instrument(word){}
        Instrument_stz(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };

    template<int Index>
    class Instrument_st_: public Instrument {
        static_assert(1 <= Index && Index <= 6, "address register index must be in [1, 6]");
    public:
        Instrument_st_(const Word& word): Instrument(word){}
        Instrument_st_(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };
}

#include "Instrument_st_.it"
