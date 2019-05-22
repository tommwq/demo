#pragma once

#include <array>
#include "Byte.hh"
#include <iostream>
#include <stdexcept>

namespace mix {
    class Word {
    public:
        Word();
        Word(long value);
        Word(const Word& rhs);
        Word(Word&& rhs);
        Word(std::initializer_list<int> initializers);
        Word& operator=(const Word& rhs);
        bool operator==(const Word& rhs) const;
        bool operator!=(const Word& rhs) const;
    public:
        long to_long() const;
        bool is_positive() const;
        bool is_negative() const;
        void set_positive();
        void set_negative();
        void set_byte(std::uint8_t position, const Byte& value);
        const Byte& get_byte(std::uint8_t position) const;
        Byte& get_byte(std::uint8_t position);
        void clear();
        void flip_sign();
    private:
        bool positive = true;
        std::array<Byte, 5> bytes;
    };

    std::ostream& operator<<(std::ostream& os, const Word& word);
}

#include "Word.inline"
