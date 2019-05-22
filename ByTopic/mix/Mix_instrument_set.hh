#include "Instrument_set.hh"

#include <cstdint>
#include <stdexcept>
#include "Instrument_lda.hh"

namespace mix {
    class Mix_instrument_set: public Instrument_set {
    public:
        Instrument& get_instrument(const Word& encoded_instrument) override;
    public:
        static const std::uint8_t LDA = 8;
        static const std::uint8_t LDX = 15;
    };
}
