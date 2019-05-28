#pragma once

#include "Instrument_io_.hh"
#include <cmath>
#include <stdexcept>
#include <iostream>
#include "Field.hh"

namespace mix {
    void Instrument_in::execute(Machine& machine) const {
        std::uint32_t address = locate(machine);
        
        std::uint8_t device_number = get_field().to_unsigned();
        std::shared_ptr<Device> device = machine.get_device(device_number);
        
        std::unique_ptr<Block> block = device->read();
        const Word* data = block->get_data();
        std::uint32_t block_size = device->get_block_size();
        for (std::uint32_t i = 0; i < block_size; i++) {
            machine.write_memory(address + i, *(data + i));
        }
    }
}
