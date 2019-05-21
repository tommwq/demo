#include "Instrument_set.hh"

#include <stdexcept>

namespace mix {
    
    class Mix_instrument_set: public Instrument_set {
    public:
        Instrument& get_instrument(const Word& encoded_instrument) override {
            throw std::runtime_error("invalid instrument");
        }
    };

}
