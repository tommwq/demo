#include "Token_type.hh"
#include <stdexcept>

namespace mix {

    bool is_instrument(const std::string& lowercase) {
        static std::string instrument_table[] = {"stj", "jmp", "cmpa", "jge", "ent2", "lda", "dec3", "j3p"};

        for (size_t i = 0; i < sizeof(instrument_table) / sizeof(instrument_table[0]); i++) {
            if (lowercase == instrument_table[i]) {
                return true;
            }
        }

        return false;
    }

    bool is_command(const std::string& lowercase) {
        static std::string command_table[] = {"orig", "equ"};

        for (size_t i = 0; i < sizeof(command_table) / sizeof(command_table[0]); i++) {
            if (lowercase == command_table[i]) {
                return true;
            }
        }
        return false;
    }

    Token_type get_token_type(const std::string& lowercase) {
        if (is_instrument(lowercase)) {
            return Token_type::Instrument;
        }

        if (is_command(lowercase)) {
            return Token_type::Command;
        }

        if (lowercase.length() == 1) {
            char ch = lowercase.at(0);
            switch (ch) {
            case '*': return Token_type::Star;
            case '+': return Token_type::Plus;
            case '-': return Token_type::Minus;
            case ',': return Token_type::Comma;
            default:
                break;
            }
        }

        return Token_type::Symbol;
    }

    std::string get_token_type_name(Token_type t) {
        switch (t) {
        case Token_type::Instrument: return "Instrument";
        case Token_type::Symbol:     return "Symbol";
        case Token_type::Number:     return "Number";
        case Token_type::Label:      return "Label";
        case Token_type::Command:    return "Command";
        case Token_type::Star:       return "Star";
        case Token_type::Plus:       return "Plus";
        case Token_type::Minus:      return "Minus";
        case Token_type::Comma:      return "Comma";
        case Token_type::Unknown:    return "Unknown";
        default:
            throw std::runtime_error("invalid token");
        }
    }
}
