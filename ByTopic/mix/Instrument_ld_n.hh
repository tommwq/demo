#pragma once

#include "Instrument.hh"

namespace mix {
    class Instrument_ld_n: public Instrument {
    public:
        Instrument_ld_n(const Word& word): Instrument(word){}
        Instrument_ld_n(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    protected:
        std::uint8_t address_register_index = 0;
    };

    class Instrument_ld1n: public Instrument_ld_n {
    public:
        Instrument_ld1n(const Word& word)
            : Instrument_ld_n(word) {
            address_register_index = 1;
        }
        Instrument_ld1n(std::initializer_list<int> initializers)
            : Instrument_ld_n(initializers) {
            address_register_index = 1;
        }
    };

    class Instrument_ld2n: public Instrument_ld_n {
    public:
        Instrument_ld2n(const Word& word)
            : Instrument_ld_n(word) {
            address_register_index = 2;
        }
        Instrument_ld2n(std::initializer_list<int> initializers)
            : Instrument_ld_n(initializers) {
            address_register_index = 2;
        }
    };

    class Instrument_ld3n: public Instrument_ld_n {
    public:
        Instrument_ld3n(const Word& word)
            : Instrument_ld_n(word) {
            address_register_index = 3;
        }
        Instrument_ld3n(std::initializer_list<int> initializers)
            : Instrument_ld_n(initializers) {
            address_register_index = 3;
        }
    };

    class Instrument_ld4n: public Instrument_ld_n {
    public:
        Instrument_ld4n(const Word& word)
            : Instrument_ld_n(word) {
            address_register_index = 4;
        }
        Instrument_ld4n(std::initializer_list<int> initializers)
            : Instrument_ld_n(initializers) {
            address_register_index = 4;
        }
    };

    class Instrument_ld5n: public Instrument_ld_n {
    public:
        Instrument_ld5n(const Word& word)
            : Instrument_ld_n(word) {
            address_register_index = 5;
        }
        Instrument_ld5n(std::initializer_list<int> initializers)
            : Instrument_ld_n(initializers) {
            address_register_index = 5;
        }
    };

    class Instrument_ld6n: public Instrument_ld_n {
    public:
        Instrument_ld6n(const Word& word)
            : Instrument_ld_n(word) {
            address_register_index = 6;
        }
        Instrument_ld6n(std::initializer_list<int> initializers)
            : Instrument_ld_n(initializers) {
            address_register_index = 6;
        }
    };
}
