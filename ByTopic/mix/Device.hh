#pragma once

#include <cstdint>
#include <memory>
#include <array>
#include "Word.hh"

namespace mix {

    class Block {
    public:
        virtual const Word* get_data() const = 0;
        virtual std::uint32_t get_size() const = 0;
    };

    template<std::uint32_t Block_size>
    class SimpleBlock: public Block, public std::array<Word, Block_size> {
    public:
        const Word* get_data() const {
            return data();
        }
        
        std::uint32_t get_size() const {
            return Block_size;
        }
    };

    class Device {
    public:
        virtual std::uint32_t get_block_size() const = 0;
        virtual std::uint32_t get_device_number() const = 0;
        virtual std::unique_ptr<Block> read() = 0;
    };
    
    template<std::uint32_t Device_number, std::uint32_t Block_size>
    class SimpleDevice: public Device {
        static_assert(Device_number <= 20, "invalid device number");
    public:
        std::uint32_t get_block_size() const override;
        std::uint32_t get_device_number() const override;
        std::unique_ptr<Block> read() override;
    };
}

#include "Device.it"
