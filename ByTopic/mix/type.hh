#pragma once

class Byte {
public:
    Byte(unsigned int value = 0) :value(value) {}
    Byte(const Byte& rhs) :value(rhs.value) {}
    
    unsigned int get_value() const {
        return value;
    }

    void set_value(Byte& value) {
        this->value = value.value;
    }

    void set_value(unsigned int value) {
        this->value = value;
    }

    Byte& operator=(const Byte& rhs) {
        value = rhs.value;
        return *this;
    }

    Byte& operator=(Byte&& rhs) = delete;

    bool operator==(const Byte& rhs) const {
        return value == rhs.value;
    }

    bool operator<(const Byte& rhs) const {
        return value < rhs.value;
    }

    static const unsigned int Max;
    static const unsigned int Max2;
    static const unsigned int Max3;
    static const unsigned int Max4;
    
private:
    unsigned int value;
};

class Word {
private:
    long to_long() const;

    void set_byte(unsigned int position, Byte& value) {
        if (position == 0 || position > 5) {
            return;
        }

        byte[position-1].set_value(value);
    }
    
    Byte get_byte(unsigned int position) {
        Byte value;
        if (position == 0 || position > 5) {
            return value;
        }

        value = byte[position - 1];
        return value;
    }
private:
    bool positive;
    Byte byte[5];
};
