#pragma once

#include <string>
#include <vector>
#include "Token.hh"

namespace mix {
    class Source_line {
    public:
        Source_line(const std::string& filename,
                    std::uint32_t line_number,
                    const std::string& source_line);
        const std::string& Source_line::get_filename() const;
        const std::uint32_t Source_line::get_line_number() const;
        const std::string& Source_line::get_source_line() const;
        void parse();
        const std::vector<Token>& get_parse_result() const;
    private:
        const std::string filename;
        std::uint32_t line_number;
        const std::string source_line;
        std::vector<Token> parse_result;
    };
}
