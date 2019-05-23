#pragma once

#include "Instrument.hh"

namespace mix {
    class Instrument_ent_: public Instrument {
    public:
        Instrument_ent_(const Word& word): Instrument(word){}
        Instrument_ent_(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    protected:
        std::uint8_t address_register_index = 0;
    };

    class Instrument_ent1: public Instrument_ent_ {
    public:
        Instrument_ent1(const Word& word)
            : Instrument_ent_(word) {
            address_register_index = 1;
        }
        Instrument_ent1(std::initializer_list<int> initializers)
            : Instrument_ent_(initializers) {
            address_register_index = 1;
        }
    };

    class Instrument_ent2: public Instrument_ent_ {
    public:
        Instrument_ent2(const Word& word)
            : Instrument_ent_(word) {
            address_register_index = 2;
        }
        Instrument_ent2(std::initializer_list<int> initializers)
            : Instrument_ent_(initializers) {
            address_register_index = 2;
        }
    };

    class Instrument_ent3: public Instrument_ent_ {
    public:
        Instrument_ent3(const Word& word)
            : Instrument_ent_(word) {
            address_register_index = 3;
        }
        Instrument_ent3(std::initializer_list<int> initializers)
            : Instrument_ent_(initializers) {
            address_register_index = 3;
        }
    };

    class Instrument_ent4: public Instrument_ent_ {
    public:
        Instrument_ent4(const Word& word)
            : Instrument_ent_(word) {
            address_register_index = 4;
        }
        Instrument_ent4(std::initializer_list<int> initializers)
            : Instrument_ent_(initializers) {
            address_register_index = 4;
        }
    };

    class Instrument_ent5: public Instrument_ent_ {
    public:
        Instrument_ent5(const Word& word)
            : Instrument_ent_(word) {
            address_register_index = 5;
        }
        Instrument_ent5(std::initializer_list<int> initializers)
            : Instrument_ent_(initializers) {
            address_register_index = 5;
        }
    };

    class Instrument_ent6: public Instrument_ent_ {
    public:
        Instrument_ent6(const Word& word)
            : Instrument_ent_(word) {
            address_register_index = 6;
        }
        Instrument_ent6(std::initializer_list<int> initializers)
            : Instrument_ent_(initializers) {
            address_register_index = 6;
        }
    };
}
