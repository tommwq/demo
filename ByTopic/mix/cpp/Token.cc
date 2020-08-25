#include "Token.hh"
#include <cctype>
#include <string>
#include <iostream>

namespace mix {
    Token::Token(const std::string filename,
                 std::uint32_t line,
                 std::uint32_t column,
                 const std::string& origin_text,
                 const std::string& text,
                 Token_type type)
        :filename(filename),
         line(line),
         column(column),
         origin_text(origin_text),
         text(text),
         type(type) {
    }

    Token::Token() {}

    std::string Token::to_string() const {
        return "token{" + get_token_type_name(type) + "," + text + "}";
    }

    const std::string& Token::get_text() const {
        return text;
    }

    bool Token::is_punct(char ch) {
        return std::string("*=-+,").find(ch) != std::string::npos;
    }

    bool Token::is_token_delimiter(char ch) {
        if (std::isspace(ch)) {
            return true;
        }

        switch (ch) {
        case ',': // fallthrough
        case '*': // fallthrough
        case '+': // fallthrough
        case '-': // fallthrough
            return true;
        default:
            return false;
        }
    }
}
