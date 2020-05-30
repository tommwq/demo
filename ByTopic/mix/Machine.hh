#pragma once

#include <array>
#include <stdexcept>
#include <memory>

#include "Toggle.hh"
#include "Indicator.hh"
#include "Word.hh"
#include "Device.hh"

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
        std::shared_ptr<Device> get_device(std::uint8_t device_number);
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
        std::array<std::shared_ptr<Device>, 21> devices = {
            std::make_shared<SimpleDevice<0, 100>>(),
            std::make_shared<SimpleDevice<1, 100>>(),
            std::make_shared<SimpleDevice<2, 100>>(),
            std::make_shared<SimpleDevice<3, 100>>(),
            std::make_shared<SimpleDevice<4, 100>>(),
            std::make_shared<SimpleDevice<5, 100>>(),
            std::make_shared<SimpleDevice<6, 100>>(),
            std::make_shared<SimpleDevice<7, 100>>(),
            std::make_shared<SimpleDevice<8, 100>>(),
            std::make_shared<SimpleDevice<9, 100>>(),
            std::make_shared<SimpleDevice<10, 100>>(),
            std::make_shared<SimpleDevice<11, 100>>(),
            std::make_shared<SimpleDevice<12, 100>>(),
            std::make_shared<SimpleDevice<13, 100>>(),
            std::make_shared<SimpleDevice<14, 100>>(),
            std::make_shared<SimpleDevice<15, 100>>(),
            std::make_shared<SimpleDevice<16, 16>>(),
            std::make_shared<SimpleDevice<17, 16>>(),
            std::make_shared<SimpleDevice<18, 24>>(),
            std::make_shared<SimpleDevice<19, 14>>(),
            std::make_shared<SimpleDevice<20, 14>>()
        };
    };

}

#include "Machine.it"
