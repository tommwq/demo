#pragma once

#include <vector>
#include "Word.hh"
#include "Source_line.hh"

namespace mix {
    class Source_file {
    public:
        const std::vector<Source_line>& get_source_lines() const;
        void add_source_line(const Source_line& line);
        void parse();
        std::vector<Word> get_parse_result();
    private:
        std::vector<Source_line> source_lines;
        std::vector<Token> parse_result;
    };
}
