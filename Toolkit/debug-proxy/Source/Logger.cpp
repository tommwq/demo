/**
 * logger.cpp
 * 实现Logger类。
 *
 * 建立日期：2015-11-25
 * 最后编辑：2016-09-30
 */

#include "PCH.hpp"
#include "Logger.hpp"

std::string Logger::_filename;
std::ofstream Logger::_file;
Logger Logger::_logger;
Logger::LogLevel Logger::_level = Logger::LogLevel::info;
std::mutex Logger::_fileMutex;
std::mutex Logger::_consoleMutex;


Logger::Logger() {}

Logger& Logger::logger() {
        return _logger;
}

bool Logger::setLogFile(std::string const &filename) {
        _file.close();
        _file.open(filename.c_str(), std::ios_base::app | std::ios_base::out);
        if (_file.is_open()) {
                _filename = filename;
                return true;
        }

        _file.open(_filename.c_str(), std::ios_base::app | std::ios_base::out);
        return _file.is_open();
}

bool Logger::setLogLevel(LogLevel level) {
        _level = level;
        return true;
}

void Logger::write(LogLevel level, std::string const &text) {
        if (static_cast<int>(level) < static_cast<int>(_level)) {
                return;
        }
  
        std::string log = levelString(level) + " " + timeString() + " > " + text;
        {
                //std::lock_guard<std::mutex> guard(_fileMutex);
                _file << log << std::endl;
        }
  
        writeToConsole(log);
}

void Logger::write(LogLevel level, char const *text) {
        if (text == NULL) {
                return;
        }
        std::string s(text);
        write(level, s);
}

void Logger::writeToConsole(std::string const &text) {
        //std::lock_guard<std::mutex> guard(_consoleMutex);
        std::cout << text << std::endl;
}

bool Logger::available() const {
        return _file.is_open();
}

std::string Logger::timeString() const {
        time_t now = std::time(NULL);
        struct tm timeInfo;
        localtime_s(&timeInfo, &now);
        std::string buffer(64, '\0');
        size_t size = std::strftime(const_cast<char*>(buffer.c_str()), buffer.length(),
                                    "%Y-%m-%d %H:%M:%S", &timeInfo);
        return buffer.substr(0, size);
}

std::string Logger::levelString(LogLevel level) const {
        switch (level) {
        case LogLevel::debug:
                return "DEBUG";
        default:
        case LogLevel::info:
                return "INFOR";
        };
}


Logger::LogLevel Logger::levelFromString(std::string const &level) const {
        if (level == "debug") {
                return LogLevel::debug;
        }
        if (level == "info") {
                return LogLevel::info;
        }
        if (level == "infor") {
                return LogLevel::info;
        }
        return LogLevel::info;
}
