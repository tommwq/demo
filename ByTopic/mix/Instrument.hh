#pragma once

#include "Byte.hh"
#include "Word.hh"
#include "Machine.hh"

namespace mix {
    class Instrument {
    public:
        virtual void execute(Machine& machine) = 0;
    public:
        static Word get_address(const Word& encoded_instrument);
        static Byte get_address_register_index(const Word& encoded_instrument);
        static Byte get_field(const Word& encoded_instrument);
        static Byte get_code(const Word& encoded_instrument);
    };
}

#include "Instrument.inline"
