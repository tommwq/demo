#pragma once

#include <cstdint>
#include <memory>
#include <array>
#include <vector>
#include "Word.hh"

namespace mix {

    class Block {
    public:
        virtual const Word* get_data() const = 0;
        virtual std::uint32_t get_size() const = 0;
    };

    class DynamicBlock: public Block {
    public:
        DynamicBlock(std::uint32_t size)
            :size(size),
             data(size) {}
        const Word* get_data() const {
            return data.data();
        }

        std::uint32_t get_size() const {
            return size;
        }
    private:
        std::uint32_t size;
        std::vector<Word> data;
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
        virtual void write(std::unique_ptr<Block> block) = 0;
        virtual bool is_ready() const = 0;
        virtual bool is_busy() const = 0;
    };

    enum class Device_status {
                              Ready, Busy
    };
    
    template<std::uint32_t Device_number, std::uint32_t Block_size>
    class SimpleDevice: public Device {
        static_assert(Device_number <= 20, "invalid device number");
    public:
        std::uint32_t get_block_size() const override;
        std::uint32_t get_device_number() const override;
        std::unique_ptr<Block> read() override;
        void write(std::unique_ptr<Block> block) override;
        bool is_ready() const;
        bool is_busy() const;
    private:
        Device_status status = Device_status::Ready;
    };
}

#include "Device.it"
