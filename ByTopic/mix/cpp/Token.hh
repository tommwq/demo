#include <algorithm>
#include <string>

#include "Token_type.hh"

namespace mix {
    
    class Token {
    public:
        Token();
        Token(const std::string filename,
              std::uint32_t line,
              std::uint32_t column,
              const std::string& origin_text,
              const std::string& text,
              Token_type type);

        const std::string& get_text() const;
        static bool is_token_delimiter(char ch);
        static bool is_punct(char ch);
        std::string to_string() const;
    private:
        std::string filename;
        std::uint32_t line;
        std::uint32_t column;
        std::string origin_text;
        std::string text;
        Token_type type;
    };
}
