#pragma once

#include "Byte.hh"

namespace mix {
    class Field {
    public:
        static std::uint8_t get_left(const Byte& field);
        static std::uint8_t get_right(const Byte& field);
    };
}

#include "Field.inline"
