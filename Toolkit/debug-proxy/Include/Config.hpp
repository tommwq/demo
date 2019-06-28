/**
 * Config.hpp
 *
 * 建立日期：2015-11-24
 * 最后编辑：2016-09-30
 */

class Config {
public:
        Config();
        /** 
         * 从命令行中寻找--config参数。这是一个json文件，读取文件内容中的参数。
         */
        bool load(int argc, char **argv);
        unsigned short proxyPort() const;
        unsigned short serverPort() const;
        std::string serverHost() const;
        std::string logFile() const;
        std::string logLevel() const;
private:
        bool getConfigFilename(int argc, char **argv);
        bool readConfigFile();
private:
        unsigned short _proxyPort;
        unsigned short _serverPort;
        std::string _serverHost;
        std::string _logFile;
        std::string _logLevel;
        std::string _configFilename;
};

