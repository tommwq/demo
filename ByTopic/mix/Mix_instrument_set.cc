#include "Mix_instrument_set.hh"

#include <cstdint>
#include <stdexcept>
#include "Instrument_lda.hh"
#include "Instrument_ldx.hh"
#include "Instrument_ld_.hh"

namespace mix {
    Instrument Mix_instrument_set::get_instrument(const Word& encoded_instrument) {
        Instrument instrument = encoded_instrument;
        std::uint8_t code = instrument.get_code().to_unsigned();
        switch (code) {
        case LDA:
            return Instrument_lda(instrument);
        case LDX:
            return Instrument_ldx(instrument);
        case LD1:
            return Instrument_ld1(instrument);
        case LD2:
            return Instrument_ld2(instrument);
        case LD3:
            return Instrument_ld3(instrument);
        case LD4:
            return Instrument_ld4(instrument);
        case LD5:
            return Instrument_ld5(instrument);
        case LD6:
            return Instrument_ld6(instrument);
        default:
            throw std::runtime_error("invalid instrument");
            break;
        }
    }
}
