#pragma once
#include <cstdint>

namespace mix {
    class Byte {
    public:
        Byte(std::uint8_t value = 0);
        Byte(const Byte& rhs);
    public:    
        std::uint8_t get_value() const;
        void set_value(const Byte& value);
        void set_value(std::uint8_t value);
        std::uint8_t to_unsigned() const;
        void clear();
        Byte& operator=(const Byte& rhs);
        bool operator==(const Byte& rhs) const;
        bool operator!=(const Byte& rhs) const;
        bool operator<(const Byte& rhs) const;
    public:    
        static const std::uint64_t Max;
        static const std::uint64_t Max2;
        static const std::uint64_t Max3;
        static const std::uint64_t Max4;
        static const std::uint64_t Max5;
    private:
        static std::uint8_t truncate(std::uint8_t value);
    private:
        std::uint8_t value;
    };
}

#include "Byte.inline"
