#pragma once

#include "Byte.hh"
#include <stdexcept>

namespace mix {
    class Field {
    public:
        static std::uint8_t get_left(const Byte& field);
        static std::uint8_t get_right(const Byte& field);
        static std::uint8_t make(std::uint8_t left, std::uint8_t right);
    };
}

#include "Field.inline"
