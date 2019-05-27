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
#include "Instrument_inca.hh"
#include "Instrument_incx.hh"
#include "Instrument_inc_.hh"
#include "Instrument_deca.hh"
#include "Instrument_decx.hh"
#include "Instrument_dec_.hh"
#include "Instrument_cmp_.hh"

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

        case CMPA:  return Instrument_cmpa(instrument);
        case CMP1:  return Instrument_cmp_<1>(instrument);
        case CMP2:  return Instrument_cmp_<2>(instrument);
        case CMP3:  return Instrument_cmp_<3>(instrument);
        case CMP4:  return Instrument_cmp_<4>(instrument);
        case CMP5:  return Instrument_cmp_<5>(instrument);
        case CMP6:  return Instrument_cmp_<6>(instrument);
        case CMPX:  return Instrument_cmpx(instrument);
            
        default: break;
        }

        throw std::runtime_error("invalid instrument");
    }

    Instrument Mix_instrument_set::get_instrument(const Word& encoded_instrument, std::uint8_t code, std::uint8_t field) {
        switch (field) {
        case 0:
            switch (code) {
            case INCA: return Instrument_inca(encoded_instrument);
            case INC1: return Instrument_inc1(encoded_instrument);
            case INC2: return Instrument_inc2(encoded_instrument);
            case INC3: return Instrument_inc3(encoded_instrument);
            case INC4: return Instrument_inc4(encoded_instrument);
            case INC5: return Instrument_inc5(encoded_instrument);
            case INC6: return Instrument_inc6(encoded_instrument);
            case INCX: return Instrument_incx(encoded_instrument);
            default: break;
            }
            break;
        case 1:
            switch (code) {
            case DECA: return Instrument_deca(encoded_instrument);
            case DEC1: return Instrument_dec1(encoded_instrument);
            case DEC2: return Instrument_dec2(encoded_instrument);
            case DEC3: return Instrument_dec3(encoded_instrument);
            case DEC4: return Instrument_dec4(encoded_instrument);
            case DEC5: return Instrument_dec5(encoded_instrument);
            case DEC6: return Instrument_dec6(encoded_instrument);
            case DECX: return Instrument_decx(encoded_instrument);
            default: break;
            }
            break;
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
