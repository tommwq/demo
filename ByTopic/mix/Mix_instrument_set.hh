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

        static const std::uint8_t ENTA = 48;
        static const std::uint8_t ENT1 = 49;
        static const std::uint8_t ENT2 = 50;
        static const std::uint8_t ENT3 = 51;
        static const std::uint8_t ENT4 = 52;
        static const std::uint8_t ENT5 = 53;
        static const std::uint8_t ENT6 = 54;
        static const std::uint8_t ENTX = 55;
    };
}
