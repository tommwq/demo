#include <cctype>

#include <algorithm>
#include <ios>
#include <fstream>
#include <array>
#include <vector>
#include <string>
#include <iostream>

#include "Source_file.hh"

int main(int argc, char *argv[]) {
    /*
      用法：assembler.exe a.mixal

      读取文件，按行分拆并加入文件名和行号。
      处理equ，建立符号表。
      处理orig。
      处理指令和标号。
      输出orig和编码后的指令。
    */

    if (argc == 1) {
        std::cout << "usage: " << argv[0] << " a.mixal" << std::endl;
        return 0;
    }

    mix::Source_file source_file;

    std::string filename = argv[1];    
    std::fstream input_file(filename, std::ios_base::in);
    std::array<char, 4096> buffer;

    for (std::uint32_t line_number = 1; !input_file.eof(); line_number++) {
        input_file.getline(buffer.data(), buffer.size());
        std::string line = buffer.data();
        source_file.add_source_line(mix::Source_line(filename, line_number, line));
    }

    for (auto& line: source_file.get_source_lines()) {
        std::cout << line.get_filename() << "+" << line.get_line_number() << ":" << line.get_source_line() << std::endl;
    }

    source_file.parse();

    return 0;
}
