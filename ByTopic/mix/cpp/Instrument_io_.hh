#pragma once

#include "Instrument.hh"

namespace mix {
    class Instrument_in: public Instrument {
    public:
        Instrument_in(const Word& word): Instrument(word){}
        Instrument_in(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };

    class Instrument_out: public Instrument {
    public:
        Instrument_out(const Word& word): Instrument(word){}
        Instrument_out(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };

    class Instrument_ioc: public Instrument {
    public:
        Instrument_ioc(const Word& word): Instrument(word){}
        Instrument_ioc(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };
}
