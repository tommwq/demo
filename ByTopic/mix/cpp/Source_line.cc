#include "Source_line.hh"
#include <cctype>
#include <algorithm>
#include <iostream>

namespace mix {
    Source_line::Source_line(const std::string& filename, std::uint32_t line_number, const std::string& source_line)
        :filename(filename),
         line_number(line_number),
         source_line(source_line) {}
    
    const std::string& Source_line::get_filename() const {
        return filename;
    }
    
    const std::uint32_t Source_line::get_line_number() const {
        return line_number;
    }
    
    const std::string& Source_line::get_source_line() const {
        return source_line;
    }

    void Source_line::parse() {
        parse_result.resize(0);

        std::string::const_iterator begin = source_line.begin();
        std::string::const_iterator end = source_line.end();
        std::string::const_iterator start = source_line.begin();
        std::string::const_iterator stop = start;

        while (start != end) {
            std::string origin_text;
            std::string text;
            
            start = find_if(stop, end, std::isalnum);
            std::string::const_iterator pos = find_if(stop, start, Token::is_punct);
            while (pos != start) {
                origin_text.assign(pos, pos + 1);
                text.resize(1);
                std::transform(origin_text.begin(), origin_text.end(), text.begin(), std::tolower);
                parse_result.push_back(Token(get_filename(),
                                             get_line_number(),
                                             (pos - begin),
                                             origin_text,
                                             text,
                                             get_token_type(text)));
                pos = find_if(pos + 1, start, Token::is_punct);
            }
            
            stop = find_if_not(start, end, std::isalnum);
            if (all_of(start, stop, std::isspace)) {
                continue;
            }

            origin_text.assign(start, stop);
            text.resize(origin_text.size());
            std::transform(origin_text.begin(), origin_text.end(), text.begin(), std::tolower);
            parse_result.push_back(Token(get_filename(),
                                         get_line_number(),
                                         (pos - begin),
                                         origin_text,
                                         text,
                                         get_token_type(text)));
        }
    }
    
    const std::vector<Token>& Source_line::get_parse_result() const {
        return parse_result;
    }
}
