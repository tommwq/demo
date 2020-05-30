#include "Mix_instrument_set.hh"

#include <cstdint>
#include <stdexcept>
#include "Instrument_ld_.hh"
#include "Instrument_ld_n.hh"
#include "Instrument_st_.hh"
#include "Instrument_add.hh"
#include "Instrument_sub.hh"
#include "Instrument_mul.hh"
#include "Instrument_div.hh"
#include "Instrument_move.hh"
#include "Instrument_ent_.hh"
#include "Instrument_enn_.hh"
#include "Instrument_inc_.hh"
#include "Instrument_dec_.hh"
#include "Instrument_cmp_.hh"
#include "Instrument_jmp_.hh"
#include "Instrument_io_.hh"
#include "Instrument_sla.hh"
#include "Instrument_sra.hh"
#include "Instrument_slax.hh"
#include "Instrument_srax.hh"
#include "Instrument_slc.hh"
#include "Instrument_src.hh"
#include "Instrument_num.hh"
#include "Instrument_char.hh"
#include "Instrument_nop.hh"
#include "Instrument_hlt.hh"

namespace mix {
    Instrument Mix_instrument_set::get_instrument(const Word& encoded_instrument) {
        Instrument instrument = encoded_instrument;
        std::uint8_t code = instrument.get_code().to_unsigned();
        std::uint8_t field = instrument.get_field().to_unsigned();

        if (39 <= code && code <= 47) {
            return get_jump_instrument(encoded_instrument, code, field);
        }

        if (48 <= code && code <= 55) {
            return get_instrument(encoded_instrument, code, field);
        }
        
        switch (code) {
        case NOP:  return Instrument_nop(instrument);
        case ADD:  return Instrument_add(instrument);
        case SUB:  return Instrument_sub(instrument);
        case MUL:  return Instrument_mul(instrument);
        case DIV:  return Instrument_div(instrument);
        case NUM:  return get_convert_instrument(encoded_instrument, code, field); /* CHAR */
        case SLA:  return get_shift_instrument(encoded_instrument, code, field); /* SRA SLAX SRAX SLC SRC */
        case MOVE: return Instrument_move(instrument);
        case LDA:  return Instrument_lda(instrument);
        case LD1:  return Instrument_ld_<1>(instrument);
        case LD2:  return Instrument_ld_<2>(instrument);
        case LD3:  return Instrument_ld_<3>(instrument);
        case LD4:  return Instrument_ld_<4>(instrument);
        case LD5:  return Instrument_ld_<5>(instrument);
        case LD6:  return Instrument_ld_<6>(instrument);
        case LDX:  return Instrument_ldx(instrument);
        case LDAN: return Instrument_ldan(instrument);
        case LD1N: return Instrument_ld_n<1>(instrument);
        case LD2N: return Instrument_ld_n<2>(instrument);
        case LD3N: return Instrument_ld_n<3>(instrument);
        case LD4N: return Instrument_ld_n<4>(instrument);
        case LD5N: return Instrument_ld_n<5>(instrument);
        case LD6N: return Instrument_ld_n<6>(instrument);
        case LDXN: return Instrument_ldxn(instrument);
        case STA:  return Instrument_sta(instrument);
        case ST1:  return Instrument_st_<1>(instrument);
        case ST2:  return Instrument_st_<2>(instrument);
        case ST3:  return Instrument_st_<3>(instrument);
        case ST4:  return Instrument_st_<4>(instrument);
        case ST5:  return Instrument_st_<5>(instrument);
        case ST6:  return Instrument_st_<6>(instrument);
        case STX:  return Instrument_stx(instrument);
        case STJ:  return Instrument_stj(instrument);
        case STZ:  return Instrument_stz(instrument);
        case JBUS: return Instrument_jbus(instrument);
        case IOC:  return Instrument_ioc(instrument);
        case IN:   return Instrument_in(instrument);
        case OUT:  return Instrument_out(instrument);
        case JRED: return Instrument_jred(instrument);
        case CMPA: return Instrument_cmpa(instrument);
        case CMP1: return Instrument_cmp_<1>(instrument);
        case CMP2: return Instrument_cmp_<2>(instrument);
        case CMP3: return Instrument_cmp_<3>(instrument);
        case CMP4: return Instrument_cmp_<4>(instrument);
        case CMP5: return Instrument_cmp_<5>(instrument);
        case CMP6: return Instrument_cmp_<6>(instrument);
        case CMPX: return Instrument_cmpx(instrument);            
        default: break;
        }

        throw std::runtime_error("invalid instrument");
    }

    Instrument Mix_instrument_set::get_instrument(const Word& encoded_instrument, std::uint8_t code, std::uint8_t field) {
        switch (field) {
        case 0:
            switch (code) {
            case INCA: return Instrument_inca(encoded_instrument);
            case INC1: return Instrument_inc_<1>(encoded_instrument);
            case INC2: return Instrument_inc_<2>(encoded_instrument);
            case INC3: return Instrument_inc_<3>(encoded_instrument);
            case INC4: return Instrument_inc_<4>(encoded_instrument);
            case INC5: return Instrument_inc_<5>(encoded_instrument);
            case INC6: return Instrument_inc_<6>(encoded_instrument);
            case INCX: return Instrument_incx(encoded_instrument);
            default: break;
            }
            break;
        case 1:
            switch (code) {
            case DECA: return Instrument_deca(encoded_instrument);
            case DEC1: return Instrument_dec_<1>(encoded_instrument);
            case DEC2: return Instrument_dec_<2>(encoded_instrument);
            case DEC3: return Instrument_dec_<3>(encoded_instrument);
            case DEC4: return Instrument_dec_<4>(encoded_instrument);
            case DEC5: return Instrument_dec_<5>(encoded_instrument);
            case DEC6: return Instrument_dec_<6>(encoded_instrument);
            case DECX: return Instrument_decx(encoded_instrument);
            default: break;
            }
            break;
        case 2:
            switch (code) {
            case ENTA: return Instrument_enta(encoded_instrument);
            case ENT1: return Instrument_ent_<1>(encoded_instrument);
            case ENT2: return Instrument_ent_<2>(encoded_instrument);
            case ENT3: return Instrument_ent_<3>(encoded_instrument);
            case ENT4: return Instrument_ent_<4>(encoded_instrument);
            case ENT5: return Instrument_ent_<5>(encoded_instrument);
            case ENT6: return Instrument_ent_<6>(encoded_instrument);
            case ENTX: return Instrument_entx(encoded_instrument);
            default: break;
            }
            break;
        case 3:
            switch (code) {
            case ENNA: return Instrument_enna(encoded_instrument);
            case ENN1: return Instrument_enn_<1>(encoded_instrument);
            case ENN2: return Instrument_enn_<2>(encoded_instrument);
            case ENN3: return Instrument_enn_<3>(encoded_instrument);
            case ENN4: return Instrument_enn_<4>(encoded_instrument);
            case ENN5: return Instrument_enn_<5>(encoded_instrument);
            case ENN6: return Instrument_enn_<6>(encoded_instrument);
            case ENNX: return Instrument_ennx(encoded_instrument);
            default: break;
            }
            break;
        default: break;
        }

        throw std::runtime_error("invalid instrument");
    }

    Instrument Mix_instrument_set::get_jump_instrument(const Word& encoded_instrument, std::uint8_t code, std::uint8_t field) {

        if (code == 39) {
            switch (field) {
            case 0: return Instrument_jump_<Machine_condition_true>(encoded_instrument);
            case 1: return Instrument_jsj(encoded_instrument);
            case 2: return Instrument_jump_<Machine_condition_overflow>(encoded_instrument);
            case 3: return Instrument_jump_<Machine_condition_not_overflow>(encoded_instrument);
            case 4: return Instrument_jump_<Machine_condition_less>(encoded_instrument);
            case 5: return Instrument_jump_<Machine_condition_equal>(encoded_instrument);
            case 6: return Instrument_jump_<Machine_condition_greater>(encoded_instrument);
            case 7: return Instrument_jump_<Machine_condition_greater_or_equal>(encoded_instrument);
            case 8: return Instrument_jump_<Machine_condition_not_equal>(encoded_instrument);
            case 9: return Instrument_jump_<Machine_condition_less_or_equal>(encoded_instrument);
            default: break;
            }
        } else if (code == 40) {
            switch (field) {
            case 0: return Instrument_jump_<Machine_condition_ra_negative>(encoded_instrument);
            case 1: return Instrument_jump_<Machine_condition_ra_zero>(encoded_instrument);
            case 2: return Instrument_jump_<Machine_condition_ra_positive>(encoded_instrument);
            case 3: return Instrument_jump_<Machine_condition_ra_not_negative>(encoded_instrument);
            case 4: return Instrument_jump_<Machine_condition_ra_not_zero>(encoded_instrument);
            case 5: return Instrument_jump_<Machine_condition_ra_not_positive>(encoded_instrument);
            default: break;
            }
        } else if (code == 41) {
            switch (field) {
            case 0: return Instrument_jump_<Machine_condition_r__negative<1>>(encoded_instrument);
            case 1: return Instrument_jump_<Machine_condition_r__zero<1>>(encoded_instrument);
            case 2: return Instrument_jump_<Machine_condition_r__positive<1>>(encoded_instrument);
            case 3: return Instrument_jump_<Machine_condition_r__not_negative<1>>(encoded_instrument);
            case 4: return Instrument_jump_<Machine_condition_r__not_zero<1>>(encoded_instrument);
            case 5: return Instrument_jump_<Machine_condition_r__not_positive<1>>(encoded_instrument);
            default: break;
            }
        } else if (code == 42) {
            switch (field) {
            case 0: return Instrument_jump_<Machine_condition_r__negative<2>>(encoded_instrument);
            case 1: return Instrument_jump_<Machine_condition_r__zero<2>>(encoded_instrument);
            case 2: return Instrument_jump_<Machine_condition_r__positive<2>>(encoded_instrument);
            case 3: return Instrument_jump_<Machine_condition_r__not_negative<2>>(encoded_instrument);
            case 4: return Instrument_jump_<Machine_condition_r__not_zero<2>>(encoded_instrument);
            case 5: return Instrument_jump_<Machine_condition_r__not_positive<2>>(encoded_instrument);
            default: break;
            }
        } else if (code == 43) {
            switch (field) {
            case 0: return Instrument_jump_<Machine_condition_r__negative<3>>(encoded_instrument);
            case 1: return Instrument_jump_<Machine_condition_r__zero<3>>(encoded_instrument);
            case 2: return Instrument_jump_<Machine_condition_r__positive<3>>(encoded_instrument);
            case 3: return Instrument_jump_<Machine_condition_r__not_negative<3>>(encoded_instrument);
            case 4: return Instrument_jump_<Machine_condition_r__not_zero<3>>(encoded_instrument);
            case 5: return Instrument_jump_<Machine_condition_r__not_positive<3>>(encoded_instrument);
            default: break;
            }
        } else if (code == 44) {
            switch (field) {
            case 0: return Instrument_jump_<Machine_condition_r__negative<4>>(encoded_instrument);
            case 1: return Instrument_jump_<Machine_condition_r__zero<4>>(encoded_instrument);
            case 2: return Instrument_jump_<Machine_condition_r__positive<4>>(encoded_instrument);
            case 3: return Instrument_jump_<Machine_condition_r__not_negative<4>>(encoded_instrument);
            case 4: return Instrument_jump_<Machine_condition_r__not_zero<4>>(encoded_instrument);
            case 5: return Instrument_jump_<Machine_condition_r__not_positive<4>>(encoded_instrument);
            default: break;
            }
        } else if (code == 45) {
            switch (field) {
            case 0: return Instrument_jump_<Machine_condition_r__negative<5>>(encoded_instrument);
            case 1: return Instrument_jump_<Machine_condition_r__zero<5>>(encoded_instrument);
            case 2: return Instrument_jump_<Machine_condition_r__positive<5>>(encoded_instrument);
            case 3: return Instrument_jump_<Machine_condition_r__not_negative<5>>(encoded_instrument);
            case 4: return Instrument_jump_<Machine_condition_r__not_zero<5>>(encoded_instrument);
            case 5: return Instrument_jump_<Machine_condition_r__not_positive<5>>(encoded_instrument);
            default: break;
            }
        } else if (code == 46) {
            switch (field) {
            case 0: return Instrument_jump_<Machine_condition_r__negative<6>>(encoded_instrument);
            case 1: return Instrument_jump_<Machine_condition_r__zero<6>>(encoded_instrument);
            case 2: return Instrument_jump_<Machine_condition_r__positive<6>>(encoded_instrument);
            case 3: return Instrument_jump_<Machine_condition_r__not_negative<6>>(encoded_instrument);
            case 4: return Instrument_jump_<Machine_condition_r__not_zero<6>>(encoded_instrument);
            case 5: return Instrument_jump_<Machine_condition_r__not_positive<6>>(encoded_instrument);
            default: break;
            }
        } else if (code == 47) {
            switch (field) {
            case 0: return Instrument_jump_<Machine_condition_rx_negative>(encoded_instrument);
            case 1: return Instrument_jump_<Machine_condition_rx_zero>(encoded_instrument);
            case 2: return Instrument_jump_<Machine_condition_rx_positive>(encoded_instrument);
            case 3: return Instrument_jump_<Machine_condition_rx_not_negative>(encoded_instrument);
            case 4: return Instrument_jump_<Machine_condition_rx_not_zero>(encoded_instrument);
            case 5: return Instrument_jump_<Machine_condition_rx_not_positive>(encoded_instrument);
            default: break;
            }
        }
        
        throw std::runtime_error("invalid instrument");
    }

    Instrument Mix_instrument_set::get_shift_instrument(const Word& encoded_instrument, std::uint8_t code, std::uint8_t field) {
        switch (field) {
        case 0: return Instrument_sla(encoded_instrument);
        case 1: return Instrument_sra(encoded_instrument);
        case 2: return Instrument_slax(encoded_instrument);
        case 3: return Instrument_srax(encoded_instrument);
        case 4: return Instrument_slc(encoded_instrument);
        case 5: return Instrument_src(encoded_instrument);
        default: break;
        }
        throw std::runtime_error("invalid instrument");
    }

    Instrument Mix_instrument_set::get_convert_instrument(const Word& encoded_instrument, std::uint8_t code, std::uint8_t field) {
        switch (field) {
        case 0: return Instrument_num(encoded_instrument);
        case 1: return Instrument_char(encoded_instrument);
        case 2: return Instrument_hlt(encoded_instrument);
        default: break;
        }
        throw std::runtime_error("invalid instrument");
    }
}
