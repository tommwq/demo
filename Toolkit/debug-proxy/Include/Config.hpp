/**
 * Config.hpp
 *
 * �������ڣ�2015-11-24
 * ���༭��2016-09-30
 */

class Config {
public:
        Config();
        /** 
         * ����������Ѱ��--config����������һ��json�ļ�����ȡ�ļ������еĲ�����
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

