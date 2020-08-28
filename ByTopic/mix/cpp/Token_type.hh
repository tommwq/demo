#pragma once

#include <string>

namespace mix {
    enum class Token_type {
                           Instrument,  // 指令，如果STJ
                           Symbol,      // 符号，如X
                           Number,      // 数值，如10
                           Label,       // 标号，如2H
                           Command,     // 命令，如ORIG
                           Star,        // 星号，即*
                           Plus,        // 加号，即+
                           Minus,       // 减号，即-
                           Comma,       // 逗号，即,
                           Unknown,     // 未知符号
    };

    Token_type get_token_type(const std::string& lowercase);
    std::string get_token_type_name(Token_type t);
}
