#pragma once

#include "Instrument.hh"

namespace mix {
    class Instrument_dec_: public Instrument {
    public:
        Instrument_dec_(const Word& word): Instrument(word){}
        Instrument_dec_(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    protected:
        std::uint8_t address_register_index = 0;
    };

    class Instrument_dec1: public Instrument_dec_ {
    public:
        Instrument_dec1(const Word& word)
            : Instrument_dec_(word) {
            address_register_index = 1;
        }
        Instrument_dec1(std::initializer_list<int> initializers)
            : Instrument_dec_(initializers) {
            address_register_index = 1;
        }
    };

    class Instrument_dec2: public Instrument_dec_ {
    public:
        Instrument_dec2(const Word& word)
            : Instrument_dec_(word) {
            address_register_index = 2;
        }
        Instrument_dec2(std::initializer_list<int> initializers)
            : Instrument_dec_(initializers) {
            address_register_index = 2;
        }
    };

    class Instrument_dec3: public Instrument_dec_ {
    public:
        Instrument_dec3(const Word& word)
            : Instrument_dec_(word) {
            address_register_index = 3;
        }
        Instrument_dec3(std::initializer_list<int> initializers)
            : Instrument_dec_(initializers) {
            address_register_index = 3;
        }
    };

    class Instrument_dec4: public Instrument_dec_ {
    public:
        Instrument_dec4(const Word& word)
            : Instrument_dec_(word) {
            address_register_index = 4;
        }
        Instrument_dec4(std::initializer_list<int> initializers)
            : Instrument_dec_(initializers) {
            address_register_index = 4;
        }
    };

    class Instrument_dec5: public Instrument_dec_ {
    public:
        Instrument_dec5(const Word& word)
            : Instrument_dec_(word) {
            address_register_index = 5;
        }
        Instrument_dec5(std::initializer_list<int> initializers)
            : Instrument_dec_(initializers) {
            address_register_index = 5;
        }
    };

    class Instrument_dec6: public Instrument_dec_ {
    public:
        Instrument_dec6(const Word& word)
            : Instrument_dec_(word) {
            address_register_index = 6;
        }
        Instrument_dec6(std::initializer_list<int> initializers)
            : Instrument_dec_(initializers) {
            address_register_index = 6;
        }
    };
}
