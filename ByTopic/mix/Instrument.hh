#pragma once

#include "Byte.hh"
#include "Word.hh"
#include "Field.hh"
#include "Machine.hh"

namespace mix {
    class Instrument: public Word {
    public:
        Instrument(): Word(){}
        Instrument(const Word& word): Word(word){}
        Instrument(std::initializer_list<int> initializers): Word(initializers){}
        virtual void execute(Machine& machine) const {};
    public:
        Word get_address() const;
        Byte get_index() const;
        Byte get_field() const;
        Byte get_code() const;
    public:
        // utility functions
        std::uint32_t locate(const Machine& machine) const;
        // 读取内存，按照field对齐，并保留符号位。
        Word load(const Machine& machine) const;
        void store(Machine& machine, const Word& value) const;
    };
}

#include "Instrument.ih"
