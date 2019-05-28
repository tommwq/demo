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
        Word read_memory(std::uint32_t real_address) const;
        void write_memory(std::uint32_t real_address, const Word& value);
        const Toggle& get_overflow_toggle() const;
        Toggle& get_overflow_toggle();
        const Indicator& get_compare_indicator() const;
        Indicator& get_compare_indicator();
        std::uint32_t get_program_counter() const;
        std::uint32_t& get_program_counter();
        void reset();
    private:
        void check_memory_address(std::uint32_t real_address) const;
    private:
        Word ra;
        Word rx;
        Word ri[6];
        Word rj;
        std::uint32_t program_counter = 0;
        Toggle overflow_toggle;
        Indicator compare_indicator;
        std::array<Word,4000> memory;
    };

}

#include "Machine.inline"
