#pragma once

#include <array>
#include "Byte.hh"

namespace mix {
    class Word {
    public:
        Word();
        Word(long value);
        Word(const Word& rhs);
        Word(Word&& rhs);
        Word(std::initializer_list<int> initializers);
    public:
        long to_long() const;
        bool is_positive() const;
        bool is_negative() const;
        void set_positive();
        void set_negative();
        void set_byte(std::uint8_t position, Byte& value);
        Byte get_byte(std::uint8_t position) const;
    private:
        bool positive = true;
        std::array<Byte, 5> bytes;
    };
}

#include "Word.inline"
