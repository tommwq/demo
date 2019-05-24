#pragma once

#include "Instrument.hh"

namespace mix {
    class Instrument_enn_: public Instrument {
    public:
        Instrument_enn_(const Word& word): Instrument(word){}
        Instrument_enn_(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    protected:
        std::uint8_t address_register_index = 0;
    };

    class Instrument_enn1: public Instrument_enn_ {
    public:
        Instrument_enn1(const Word& word)
            : Instrument_enn_(word) {
            address_register_index = 1;
        }
        Instrument_enn1(std::initializer_list<int> initializers)
            : Instrument_enn_(initializers) {
            address_register_index = 1;
        }
    };

    class Instrument_enn2: public Instrument_enn_ {
    public:
        Instrument_enn2(const Word& word)
            : Instrument_enn_(word) {
            address_register_index = 2;
        }
        Instrument_enn2(std::initializer_list<int> initializers)
            : Instrument_enn_(initializers) {
            address_register_index = 2;
        }
    };

    class Instrument_enn3: public Instrument_enn_ {
    public:
        Instrument_enn3(const Word& word)
            : Instrument_enn_(word) {
            address_register_index = 3;
        }
        Instrument_enn3(std::initializer_list<int> initializers)
            : Instrument_enn_(initializers) {
            address_register_index = 3;
        }
    };

    class Instrument_enn4: public Instrument_enn_ {
    public:
        Instrument_enn4(const Word& word)
            : Instrument_enn_(word) {
            address_register_index = 4;
        }
        Instrument_enn4(std::initializer_list<int> initializers)
            : Instrument_enn_(initializers) {
            address_register_index = 4;
        }
    };

    class Instrument_enn5: public Instrument_enn_ {
    public:
        Instrument_enn5(const Word& word)
            : Instrument_enn_(word) {
            address_register_index = 5;
        }
        Instrument_enn5(std::initializer_list<int> initializers)
            : Instrument_enn_(initializers) {
            address_register_index = 5;
        }
    };

    class Instrument_enn6: public Instrument_enn_ {
    public:
        Instrument_enn6(const Word& word)
            : Instrument_enn_(word) {
            address_register_index = 6;
        }
        Instrument_enn6(std::initializer_list<int> initializers)
            : Instrument_enn_(initializers) {
            address_register_index = 6;
        }
    };
}
