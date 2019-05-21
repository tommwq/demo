#pragma once

#include "Machine.hh"
#include "Instrument_set.hh"

namespace mix {

    class Mix {
    public:
        Mix();
        ~Mix();
        Mix(const Mix&) = delete;
        Mix& operator=(const Mix&) = delete;
        Mix(Mix&&) = delete;
        Mix& operator=(Mix&&) = delete;

        Machine make_snapshot() const;
        
        
    protected:
        Instrument_set& instrument_set;
        Machine machine;
    };
}

#include "Mix.inline"
