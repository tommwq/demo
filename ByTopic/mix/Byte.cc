#include "Byte.hh"

namespace mix {

    const std::uint64_t Byte::Max = 63;
    const std::uint64_t Byte::Max2 = 4095;
    const std::uint64_t Byte::Max3 = 16777215;
    const std::uint64_t Byte::Max4 = 1073741823;
    
    Byte::Byte(std::uint8_t value) :value(truncate(value)) {}
    Byte::Byte(const Byte& rhs) :value(rhs.value) {}
}
