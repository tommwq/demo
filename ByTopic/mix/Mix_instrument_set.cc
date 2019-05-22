#include "Mix_instrument_set.hh"

#include <cstdint>
#include <stdexcept>
#include "Instrument_lda.hh"

namespace mix {
    Instrument& Mix_instrument_set::get_instrument(const Word& encoded_instrument) {
        Instrument instrument = encoded_instrument;
        std::uint8_t code = instrument.get_code().to_unsigned();
        switch (code) {
        case LDA:
            return Instrument_lda(instrument);
        default:
            throw std::runtime_error("invalid instrument");
            break;
        }
    }
}
