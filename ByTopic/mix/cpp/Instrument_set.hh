#pragma once

#include "Instrument.hh"

namespace mix {
    class Instrument_set {
    public:
        virtual Instrument get_instrument(const Word& encoded_instrument) = 0;
    };
}
