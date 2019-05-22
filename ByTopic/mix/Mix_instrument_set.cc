#include "Mix_instrument_set.hh"

#include <cstdint>
#include <stdexcept>
#include "Instrument_lda.hh"
#include "Instrument_ldx.hh"
#include "Instrument_ld_.hh"
#include "Instrument_ldan.hh"
#include "Instrument_ldxn.hh"
#include "Instrument_ld_n.hh"
#include "Instrument_sta.hh"
#include "Instrument_stx.hh"
#include "Instrument_st_.hh"
#include "Instrument_stj.hh"
#include "Instrument_stz.hh"
#include "Instrument_add.hh"

namespace mix {
    Instrument Mix_instrument_set::get_instrument(const Word& encoded_instrument) {
        Instrument instrument = encoded_instrument;
        std::uint8_t code = instrument.get_code().to_unsigned();
        switch (code) {
        case ADD:  return Instrument_add(instrument);
            
        case LDA:  return Instrument_lda(instrument);
        case LD1:  return Instrument_ld1(instrument);
        case LD2:  return Instrument_ld2(instrument);
        case LD3:  return Instrument_ld3(instrument);
        case LD4:  return Instrument_ld4(instrument);
        case LD5:  return Instrument_ld5(instrument);
        case LD6:  return Instrument_ld6(instrument);
        case LDX:  return Instrument_ldx(instrument);
        case LDAN: return Instrument_ldan(instrument);
        case LDXN: return Instrument_ldxn(instrument);
        case LD1N: return Instrument_ld1n(instrument);
        case LD2N: return Instrument_ld2n(instrument);
        case LD3N: return Instrument_ld3n(instrument);
        case LD4N: return Instrument_ld4n(instrument);
        case LD5N: return Instrument_ld5n(instrument);
        case LD6N: return Instrument_ld6n(instrument);
        case STA:  return Instrument_sta(instrument);
        case ST1:  return Instrument_st1(instrument);
        case ST2:  return Instrument_st2(instrument);
        case ST3:  return Instrument_st3(instrument);
        case ST4:  return Instrument_st4(instrument);
        case ST5:  return Instrument_st5(instrument);
        case ST6:  return Instrument_st6(instrument);
        case STX:  return Instrument_stx(instrument);
        case STJ:  return Instrument_stj(instrument);
        case STZ:  return Instrument_stz(instrument);
        default:   throw std::runtime_error("invalid instrument");
        }
    }
}
