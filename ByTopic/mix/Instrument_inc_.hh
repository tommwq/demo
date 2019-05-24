#pragma once

#include "Instrument.hh"

namespace mix {
    class Instrument_inc_: public Instrument {
    public:
        Instrument_inc_(const Word& word): Instrument(word){}
        Instrument_inc_(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    protected:
        std::uint8_t address_register_index = 0;
    };

    class Instrument_inc1: public Instrument_inc_ {
    public:
        Instrument_inc1(const Word& word)
            : Instrument_inc_(word) {
            address_register_index = 1;
        }
        Instrument_inc1(std::initializer_list<int> initializers)
            : Instrument_inc_(initializers) {
            address_register_index = 1;
        }
    };

    class Instrument_inc2: public Instrument_inc_ {
    public:
        Instrument_inc2(const Word& word)
            : Instrument_inc_(word) {
            address_register_index = 2;
        }
        Instrument_inc2(std::initializer_list<int> initializers)
            : Instrument_inc_(initializers) {
            address_register_index = 2;
        }
    };

    class Instrument_inc3: public Instrument_inc_ {
    public:
        Instrument_inc3(const Word& word)
            : Instrument_inc_(word) {
            address_register_index = 3;
        }
        Instrument_inc3(std::initializer_list<int> initializers)
            : Instrument_inc_(initializers) {
            address_register_index = 3;
        }
    };

    class Instrument_inc4: public Instrument_inc_ {
    public:
        Instrument_inc4(const Word& word)
            : Instrument_inc_(word) {
            address_register_index = 4;
        }
        Instrument_inc4(std::initializer_list<int> initializers)
            : Instrument_inc_(initializers) {
            address_register_index = 4;
        }
    };

    class Instrument_inc5: public Instrument_inc_ {
    public:
        Instrument_inc5(const Word& word)
            : Instrument_inc_(word) {
            address_register_index = 5;
        }
        Instrument_inc5(std::initializer_list<int> initializers)
            : Instrument_inc_(initializers) {
            address_register_index = 5;
        }
    };

    class Instrument_inc6: public Instrument_inc_ {
    public:
        Instrument_inc6(const Word& word)
            : Instrument_inc_(word) {
            address_register_index = 6;
        }
        Instrument_inc6(std::initializer_list<int> initializers)
            : Instrument_inc_(initializers) {
            address_register_index = 6;
        }
    };
}
