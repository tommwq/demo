'''
text_file_filter.py
过滤文本文件。

2017年10月18日 tommwq

用法：
python text_file_filter.py -in FILENAME1 [-out FILENAME2] [-match any|none|all] -words "a,b,c" [-max 1000] [-line-number] [-trim-space]

'''

# TODO 支持中文

import sys
from enum import Enum


class TextFilterMode(Enum):
    match_all = 1
    match_any = 2
    match_none = 3
    def from_string(s):
        s = s.lower()
        if s == "any":
            return TextFilterMode.match_any
        elif s == "none":
            return TextFilterMode.match_none
        else:
            return TextFilterMode.match_all

class LineMatcher():
    def __init__(self, keywords, filter_mode):
        self.keywords = keywords
        self.filter_mode = filter_mode
    def match(self, line):
        if self.filter_mode == TextFilterMode.match_all:
            return self._match_all(line)
        elif self.filter_mode == TextFilterMode.match_any:
            return self._match_any(line)
        elif self.filter_mode == TextFilterMode.match_none:
            return self._match_none(line)
        else:
            return False
    def _match_all(self, line):
        for keyword in self.keywords:
            if line.find(keyword) == -1:
                return False
        return True
    def _match_any(self, line):
        for keyword in self.keywords:
            if line.find(keyword) != -1:
                return True
        return False
    def _match_none(self, line):
        for keyword in self.keywords:
            if line.find(keyword) != -1:
                return False
        return True


# TODO 写一个过滤日志的脚本。传入日志名和关键字。
class TextFileFilter:
    def __init__(self):
        self.keywords = []
        self.encoding = "utf-8"
        self.max_output_line = -1
        self.filter_mode = TextFilterMode.match_all
        self.output_filename = ""
        self.input_filename = ""
        self.show_line_number = False
        self.trim_space = False
    def set_show_line_number(self, visible):
        self.show_line_number = visible
    def set_trim_space(self, visible):
        self.trim_space = visible
    def set_max_output_line(self, count):
        self.max_output_line = count
    def set_filter_mode(self, filter_mode):
        self.filter_mode = filter_mode
    def set_input(self, filename):
        self.input_filename = filename
        return self
    def set_output(self, filename):
        self.output_filename = filename
        return self
    def clear_keyworks(self):
        self.keywords = []
        return self
    def add_keywords(self, keyword):
        self.keywords.append(keyword)
    def set_encoding(self, encoding):
        self.encoding = encoding
    def filter(self):
        if self.output_filename == "":
            self._filter_to_file(sys.stdout)
        else:
            with open(self.output_filename, encoding=self.encoding, mode="w") as output_file:
                self._filter_to_file(output_file)
    def _filter_to_file(self, output_file):
        self.line_matcher = LineMatcher(self.keywords, self.filter_mode)
        line_number = 0
        count = 1
        with open(self.input_filename, encoding=self.encoding) as input_file:
            for line in input_file:
                line_number = line_number + 1
                if not self.line_matcher.match(line):
                    continue
                if self.trim_space:
                    line = line.strip(" \t\v")
                if self.show_line_number:
                    line = str(line_number) + ": " + line
                output_file.write(line)
                count = count + 1
                if self.max_output_line > -1 and count >= self.max_output_line:
                    break


                    
"""
解析命令行参数。
"""
class CommandLineParameters:
    def __init__(self, argv):
        self.params = {}
        key = ""
        for text in argv:
            if text.startswith("-"):
                key = text.strip("-")
                self.params[key] = True
                continue
            else:
                if key == "":
                    continue
                value = text.strip()
                self.params[key] = value
                key = ""
    def has(self, param):
        return param in self.params
    def get(self, param):
        if self.has(param):
            return self.params[param]
        else:
            return None
    def get_all(self):
        return self.params
    

def main():
    param = CommandLineParameters(sys.argv)
    if not param.has("in") or not param.has("words"):
        print("missing required parameter(s).")
        return -1

    filter = TextFileFilter()
    filter.set_input(param.get("in"))
    if param.has("out"):
        filter.set_output(param.get("out"))
    if param.has("max"):
        try:
            m = int(param.get("max"))
            filter.set_max_output_line(m)
        except:
            pass
    if param.has("mode"):
        filter.set_filter_mode(TextFilterMode.from_string(param.get("mode")))

    if param.has("line-number"):
        filter.set_show_line_number(True)
        
    if param.has("trim-space"):
        filter.set_trim_space(True)
        
    keywords = param.get("words").split(",")
    for keyword in keywords:
        filter.add_keywords(keyword)
    filter.filter()
    
    return 0


if __name__ == "__main__":
    sys.exit(main())

