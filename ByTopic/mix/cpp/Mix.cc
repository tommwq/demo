#include "Mix.hh"

#include <stdexcept>
#include "Mix_instrument_set.hh"

namespace mix {

    Mix::Mix()
        :instrument_set(Mix_instrument_set()){
    }
    
    Mix::~Mix() {
    }
}
