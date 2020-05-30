#pragma once

#include <vector>
#include "Source_line.hh"
#include "Source_file.hh"

namespace mix {
    const std::vector<Source_line>& Source_file::get_source_lines() const {
        return source_lines;
    }
    void Source_file::add_source_line(const Source_line& line) {
        source_lines.push_back(line);
    }
    
    void Source_file::parse() {
        parse_result.resize(0);
        
        for (auto& line: source_lines) {
            line.parse();
            const std::vector<Token> tokens = line.get_parse_result();
            for (auto& t: tokens) {
                std::cout << t.to_string() << std::endl;
            }
        }
    }

    std::vector<Word> Source_file::get_parse_result() {
        std::vector<Word> result;
        return result;
    }
}
