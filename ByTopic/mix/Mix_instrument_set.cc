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
#include "Instrument_sub.hh"
#include "Instrument_mul.hh"
#include "Instrument_div.hh"
#include "Instrument_enta.hh"
#include "Instrument_entx.hh"
#include "Instrument_ent_.hh"
#include "Instrument_enna.hh"
#include "Instrument_ennx.hh"
#include "Instrument_enn_.hh"

namespace mix {
    Instrument Mix_instrument_set::get_instrument(const Word& encoded_instrument) {
        Instrument instrument = encoded_instrument;
        std::uint8_t code = instrument.get_code().to_unsigned();
        std::uint8_t field = instrument.get_field().to_unsigned();

        if (48 <= code && code <= 55) {
            return get_instrument(encoded_instrument, code, field);
        }
        
        switch (code) {
        case ADD:  return Instrument_add(instrument);
        case SUB:  return Instrument_sub(instrument);
        case MUL:  return Instrument_mul(instrument);
        case DIV:  return Instrument_div(instrument);
            
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
            
        default: break;
        }

        throw std::runtime_error("invalid instrument");
    }

    Instrument Mix_instrument_set::get_instrument(const Word& encoded_instrument, std::uint8_t code, std::uint8_t field) {
        switch (field) {
        case 2:
            switch (code) {
            case ENTA: return Instrument_enta(encoded_instrument);
            case ENT1: return Instrument_ent1(encoded_instrument);
            case ENT2: return Instrument_ent2(encoded_instrument);
            case ENT3: return Instrument_ent3(encoded_instrument);
            case ENT4: return Instrument_ent4(encoded_instrument);
            case ENT5: return Instrument_ent5(encoded_instrument);
            case ENT6: return Instrument_ent6(encoded_instrument);
            case ENTX: return Instrument_entx(encoded_instrument);
            default: break;
            }
            break;
        case 3:
            switch (code) {
            case ENNA: return Instrument_enna(encoded_instrument);
            case ENN1: return Instrument_enn1(encoded_instrument);
            case ENN2: return Instrument_enn2(encoded_instrument);
            case ENN3: return Instrument_enn3(encoded_instrument);
            case ENN4: return Instrument_enn4(encoded_instrument);
            case ENN5: return Instrument_enn5(encoded_instrument);
            case ENN6: return Instrument_enn6(encoded_instrument);
            case ENNX: return Instrument_ennx(encoded_instrument);
            default: break;
            }
            break;
        default: break;
        }

        throw std::runtime_error("invalid instrument");
    }
}
