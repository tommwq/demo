#pragma once

#include "Instrument.hh"

namespace mix {
    class Instrument_st_: public Instrument {
    public:
        Instrument_st_(const Word& word): Instrument(word){}
        Instrument_st_(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    protected:
        std::uint8_t address_register_index = 0;
    };

    class Instrument_st1: public Instrument_st_ {
    public:
        Instrument_st1(const Word& word)
            : Instrument_st_(word) {
            address_register_index = 1;
        }
        Instrument_st1(std::initializer_list<int> initializers)
            : Instrument_st_(initializers) {
            address_register_index = 1;
        }
    };

    class Instrument_st2: public Instrument_st_ {
    public:
        Instrument_st2(const Word& word)
            : Instrument_st_(word) {
            address_register_index = 2;
        }
        Instrument_st2(std::initializer_list<int> initializers)
            : Instrument_st_(initializers) {
            address_register_index = 2;
        }
    };

    class Instrument_st3: public Instrument_st_ {
    public:
        Instrument_st3(const Word& word)
            : Instrument_st_(word) {
            address_register_index = 3;
        }
        Instrument_st3(std::initializer_list<int> initializers)
            : Instrument_st_(initializers) {
            address_register_index = 3;
        }
    };

    class Instrument_st4: public Instrument_st_ {
    public:
        Instrument_st4(const Word& word)
            : Instrument_st_(word) {
            address_register_index = 4;
        }
        Instrument_st4(std::initializer_list<int> initializers)
            : Instrument_st_(initializers) {
            address_register_index = 4;
        }
    };

    class Instrument_st5: public Instrument_st_ {
    public:
        Instrument_st5(const Word& word)
            : Instrument_st_(word) {
            address_register_index = 5;
        }
        Instrument_st5(std::initializer_list<int> initializers)
            : Instrument_st_(initializers) {
            address_register_index = 5;
        }
    };

    class Instrument_st6: public Instrument_st_ {
    public:
        Instrument_st6(const Word& word)
            : Instrument_st_(word) {
            address_register_index = 6;
        }
        Instrument_st6(std::initializer_list<int> initializers)
            : Instrument_st_(initializers) {
            address_register_index = 6;
        }
    };
}
