#pragma once

#include "Byte.hh"
#include "Word.hh"
#include "Machine.hh"

namespace mix {
    class Instrument: public Word {
    public:
        Instrument(): Word(){}
        Instrument(const Word& word): Word(word){}
        Instrument(std::initializer_list<int> initializers): Word(initializers){}
        virtual void execute(Machine& machine){};
    public:
        Word get_address();
        Byte get_address_register_index();
        Byte get_field();
        Byte get_code();
    };
}

#include "Instrument.inline"
