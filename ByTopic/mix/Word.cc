#include "Word.hh"

namespace mix {

    Word::Word()
        :positive(true) {}

    Word::Word(long value) {
        assign(value);
    }

    Word::Word(const Word& rhs)
        :positive(rhs.positive), bytes(rhs.bytes) {
    }

    Word::Word(Word&& rhs)
        :positive(rhs.positive) {
        bytes.swap(rhs.bytes);
        rhs.set_positive();
    }

    Word::Word(std::initializer_list<int> initializers) {

        std::uint8_t position = 0;
        for (int x: initializers) {
            if (position > 5) {
                break;
            }
                
            if (position == 0 && x == 0) {
                set_negative();
                position++;
                continue;
            }
                
            set_byte(position, Byte(x));
            position++;
        }
    }
    
    long Word::to_long() const {
        long value = 0;
        for (const Byte& b: bytes) {
            value = (value << 6) + b.get_value();
        }

        if (is_negative()) {
            value *= -1;
        }

        return value;
    }

    std::ostream& operator<<(std::ostream& os, const Word& word) {
        os << "Word{"
           << (word.is_positive() ? "+" : "-") << ", "
           << static_cast<int>(word.get_byte(1).to_unsigned()) << ", "
           << static_cast<int>(word.get_byte(2).to_unsigned()) << ", "
           << static_cast<int>(word.get_byte(3).to_unsigned()) << ", "
           << static_cast<int>(word.get_byte(4).to_unsigned()) << ", "
           << static_cast<int>(word.get_byte(5).to_unsigned()) << "}";
        return os;
    }
}
