#pragma once

#include "Toggle.hh"
#include "Indicator.hh"
#include "Word.hh"

namespace mix {
    class Machine {
    public:
        Word& get_ra() {
            return ra;
        }
    
    private:
        Word ra;
        Word rx;
        Word ri[6];
        Word rj;
        Toogle overflow;
        Indicator compare;
        Word memory[4000];
    };

}
