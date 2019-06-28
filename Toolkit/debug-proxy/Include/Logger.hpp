/**
 * Logger.hpp
 *
 * 声明Log类。Log类负责打印日志。
 *
 * 建立日期：2015-11-25
 * 最后编辑：2019年06月28日
 */

#pragma once

class Logger {
public:
    enum class LogLevel { 
                         debug = 0, 
                         info = 1 
    };
public:
    static Logger& logger();
    /** not thread safe. */
    static bool setLogFile(std::string const &filename);
    /** not thread safe. */
    static bool setLogLevel(LogLevel level);
    void write(LogLevel level, std::string const &text);
    void write(LogLevel level, char const *text);
    bool available() const;
    LogLevel levelFromString(std::string const &level) const;
private:
    Logger();
    std::string timeString() const;
    std::string levelString(LogLevel level) const;
    void writeToConsole(std::string const &text);
private:
    static std::string _filename;
    static std::ofstream _file;
    static Logger _logger;
    static LogLevel _level;
    static std::mutex _fileMutex;
    static std::mutex _consoleMutex;
};

    
