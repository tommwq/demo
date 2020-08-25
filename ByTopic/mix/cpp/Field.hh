#pragma once

#include "Byte.hh"
#include <stdexcept>

namespace mix {
    class Field {
    public:
        static std::uint8_t get_left(const Byte& field);
        static std::uint8_t get_right(const Byte& field);
        static std::uint8_t make(std::uint8_t left, std::uint8_t right);
    public:
        Field(std::uint8_t encoded = 0);
        std::uint8_t get_left() const;
        std::uint8_t get_right() const;
        bool contains_sign() const;
    private:
        std::uint8_t left;
        std::uint8_t right;
    };
}

#include "Field.it"
