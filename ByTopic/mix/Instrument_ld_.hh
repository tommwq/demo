#pragma once

#include "Instrument.hh"

namespace mix {
    class Instrument_ld_: public Instrument {
    public:
        Instrument_ld_(const Word& word): Instrument(word){}
        Instrument_ld_(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    protected:
        std::uint8_t address_register_index = 0;
    };

    class Instrument_ld1: public Instrument_ld_ {
    public:
        Instrument_ld1(const Word& word)
            : Instrument_ld_(word) {
            address_register_index = 1;
        }
        Instrument_ld1(std::initializer_list<int> initializers)
            : Instrument_ld_(initializers) {
            address_register_index = 1;
        }
    };

    class Instrument_ld2: public Instrument_ld_ {
    public:
        Instrument_ld2(const Word& word)
            : Instrument_ld_(word) {
            address_register_index = 2;
        }
        Instrument_ld2(std::initializer_list<int> initializers)
            : Instrument_ld_(initializers) {
            address_register_index = 2;
        }
    };

    class Instrument_ld3: public Instrument_ld_ {
    public:
        Instrument_ld3(const Word& word)
            : Instrument_ld_(word) {
            address_register_index = 3;
        }
        Instrument_ld3(std::initializer_list<int> initializers)
            : Instrument_ld_(initializers) {
            address_register_index = 3;
        }
    };

    class Instrument_ld4: public Instrument_ld_ {
    public:
        Instrument_ld4(const Word& word)
            : Instrument_ld_(word) {
            address_register_index = 4;
        }
        Instrument_ld4(std::initializer_list<int> initializers)
            : Instrument_ld_(initializers) {
            address_register_index = 4;
        }
    };

    class Instrument_ld5: public Instrument_ld_ {
    public:
        Instrument_ld5(const Word& word)
            : Instrument_ld_(word) {
            address_register_index = 5;
        }
        Instrument_ld5(std::initializer_list<int> initializers)
            : Instrument_ld_(initializers) {
            address_register_index = 5;
        }
    };

    class Instrument_ld6: public Instrument_ld_ {
    public:
        Instrument_ld6(const Word& word)
            : Instrument_ld_(word) {
            address_register_index = 6;
        }
        Instrument_ld6(std::initializer_list<int> initializers)
            : Instrument_ld_(initializers) {
            address_register_index = 6;
        }
    };
}
