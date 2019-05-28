#include "Instrument_set.hh"

#include <cstdint>
#include <stdexcept>
#include "Instrument_lda.hh"

namespace mix {
    class Mix_instrument_set: public Instrument_set {
    public:
        Instrument get_instrument(const Word& encoded_instrument) override;
    private:
        Instrument get_instrument(const Word& encoded_instrument, std::uint8_t code, std::uint8_t field);
        Instrument get_jump_instrument(const Word& encoded_instrument, std::uint8_t code, std::uint8_t field);
    public:
        static const std::uint8_t ADD = 1;
        static const std::uint8_t SUB = 2;
        static const std::uint8_t MUL = 3;
        static const std::uint8_t DIV = 4;
        
        static const std::uint8_t LDA = 8;       
        static const std::uint8_t LD1 = 9;
        static const std::uint8_t LD2 = 10;
        static const std::uint8_t LD3 = 11;
        static const std::uint8_t LD4 = 12;
        static const std::uint8_t LD5 = 13;
        static const std::uint8_t LD6 = 14;
        static const std::uint8_t LDX = 15;
        static const std::uint8_t LDAN = 16;       
        static const std::uint8_t LD1N = 17;
        static const std::uint8_t LD2N = 18;
        static const std::uint8_t LD3N = 19;
        static const std::uint8_t LD4N = 20;
        static const std::uint8_t LD5N = 21;
        static const std::uint8_t LD6N = 22;
        static const std::uint8_t LDXN = 23;
        static const std::uint8_t STA = 24;
        static const std::uint8_t ST1 = 25;
        static const std::uint8_t ST2 = 26;
        static const std::uint8_t ST3 = 27;
        static const std::uint8_t ST4 = 28;
        static const std::uint8_t ST5 = 29;
        static const std::uint8_t ST6 = 30;
        static const std::uint8_t STX = 31;
        static const std::uint8_t STJ = 32;
        static const std::uint8_t STZ = 33;

        static const std::uint8_t JMP = 39;
        static const std::uint8_t JSJ = 39;
        static const std::uint8_t JOV = 39;
        static const std::uint8_t JNOV = 39;
        static const std::uint8_t JL = 39;
        static const std::uint8_t JE = 39;
        static const std::uint8_t JG = 39;
        static const std::uint8_t JGE = 39;
        static const std::uint8_t JNE = 39;
        static const std::uint8_t JLE = 39;
        static const std::uint8_t JAN = 40;
        static const std::uint8_t JAZ = 40;
        static const std::uint8_t JAP = 40;
        static const std::uint8_t JANN = 40;
        static const std::uint8_t JANZ = 40;
        static const std::uint8_t JANP = 40;
        static const std::uint8_t J1N = 41;
        static const std::uint8_t J1Z = 41;
        static const std::uint8_t J1P = 41;
        static const std::uint8_t J1NN = 41;
        static const std::uint8_t J1NZ = 41;
        static const std::uint8_t J1NP = 41;
        static const std::uint8_t J2N = 42;
        static const std::uint8_t J2Z = 42;
        static const std::uint8_t J2P = 42;
        static const std::uint8_t J2NN = 42;
        static const std::uint8_t J2NZ = 42;
        static const std::uint8_t J2NP = 42;
        static const std::uint8_t J3N = 43;
        static const std::uint8_t J3Z = 43;
        static const std::uint8_t J3P = 43;
        static const std::uint8_t J3NN = 43;
        static const std::uint8_t J3NZ = 43;
        static const std::uint8_t J3NP = 43;
        static const std::uint8_t J4N = 44;
        static const std::uint8_t J4Z = 44;
        static const std::uint8_t J4P = 44;
        static const std::uint8_t J4NN = 44;
        static const std::uint8_t J4NZ = 44;
        static const std::uint8_t J4NP = 44;
        static const std::uint8_t J5N = 45;
        static const std::uint8_t J5Z = 45;
        static const std::uint8_t J5P = 45;
        static const std::uint8_t J5NN = 45;
        static const std::uint8_t J5NZ = 45;
        static const std::uint8_t J5NP = 45;
        static const std::uint8_t J6N = 46;
        static const std::uint8_t J6Z = 46;
        static const std::uint8_t J6P = 46;
        static const std::uint8_t J6NN = 46;
        static const std::uint8_t J6NZ = 46;
        static const std::uint8_t J6NP = 46;
        static const std::uint8_t JXN = 47;
        static const std::uint8_t JXZ = 47;
        static const std::uint8_t JXP = 47;
        static const std::uint8_t JXNN = 47;
        static const std::uint8_t JXNZ = 47;
        static const std::uint8_t JXNP = 47;

        static const std::uint8_t ENTA = 48;
        static const std::uint8_t ENT1 = 49;
        static const std::uint8_t ENT2 = 50;
        static const std::uint8_t ENT3 = 51;
        static const std::uint8_t ENT4 = 52;
        static const std::uint8_t ENT5 = 53;
        static const std::uint8_t ENT6 = 54;
        static const std::uint8_t ENTX = 55;

        static const std::uint8_t ENNA = 48;
        static const std::uint8_t ENN1 = 49;
        static const std::uint8_t ENN2 = 50;
        static const std::uint8_t ENN3 = 51;
        static const std::uint8_t ENN4 = 52;
        static const std::uint8_t ENN5 = 53;
        static const std::uint8_t ENN6 = 54;
        static const std::uint8_t ENNX = 55;

        static const std::uint8_t INCA = 48;
        static const std::uint8_t INC1 = 49;
        static const std::uint8_t INC2 = 50;
        static const std::uint8_t INC3 = 51;
        static const std::uint8_t INC4 = 52;
        static const std::uint8_t INC5 = 53;
        static const std::uint8_t INC6 = 54;
        static const std::uint8_t INCX = 55;

        static const std::uint8_t DECA = 48;
        static const std::uint8_t DEC1 = 49;
        static const std::uint8_t DEC2 = 50;
        static const std::uint8_t DEC3 = 51;
        static const std::uint8_t DEC4 = 52;
        static const std::uint8_t DEC5 = 53;
        static const std::uint8_t DEC6 = 54;
        static const std::uint8_t DECX = 55;

        static const std::uint8_t CMPA = 56;
        static const std::uint8_t CMP1 = 57;
        static const std::uint8_t CMP2 = 58;
        static const std::uint8_t CMP3 = 59;
        static const std::uint8_t CMP4 = 60;
        static const std::uint8_t CMP5 = 61;
        static const std::uint8_t CMP6 = 62;
        static const std::uint8_t CMPX = 63;
        
    };
}
