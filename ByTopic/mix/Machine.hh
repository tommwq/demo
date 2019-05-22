#pragma once

#include <array>
#include <stdexcept>

#include "Toggle.hh"
#include "Indicator.hh"
#include "Word.hh"


namespace mix {
    class Machine {
    public:
        Word& get_ra();
        Word& get_rx();
        Word& get_rj();
        Word& get_ri(std::uint8_t index); // index: [1-6];
        const Word& get_ra() const;
        const Word& get_rx() const;
        const Word& get_rj() const;
        const Word& get_ri(std::uint8_t index) const; // index: [1-6];
        bool is_overflow() const;
        bool is_less() const;
        bool is_equal() const;
        bool is_greater() const;
        Word read_memory(std::uint32_t real_address);
        void write_memory(std::uint32_t real_address, const Word& value);
        Toggle get_overflow_toggle();
        Indicator get_compare_indicator();
        void reset();
    private:
        void check_memory_address(std::uint32_t real_address);
    private:
        Word ra;
        Word rx;
        Word ri[6];
        Word rj;
        Toggle overflow_toggle;
        Indicator compare_indicator;
        std::array<Word,4000> memory;
    };

}

#include "Machine.inline"
