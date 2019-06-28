/**
 * config.cpp
 *
 * 实现Config类。
 *
 * 建立日期：2015-11-25
 * 最后编辑：2016-09-29
 */

#include "PCH.hpp"
#include <rapidjson/document.h>
#include <rapidjson/writer.h>
#include <rapidjson/stringbuffer.h>
#include "Config.hpp"

Config::Config()
        :_logLevel("debug"),
         _logFile("debugProxy.log") {}


bool Config::getConfigFilename(int argc, char **argv) {
        for (int i = 1; i < argc; ++i) {
                std::string arg = argv[i];
                if (arg == "--config" && i < argc - 1) {
                        _configFilename = argv[i + 1];
                        return true;
                }
        }
        return false;
}

bool Config::readConfigFile() {
        std::ifstream file(_configFilename.c_str());
        if (!file.is_open()) {
                return false;
        }

        file.seekg(0, std::ios_base::end);
        int const fileSize = file.tellg();
        std::string buffer(fileSize, '\0');
        file.seekg(0, std::ios_base::beg);
        file.read(const_cast<char*>(buffer.c_str()), fileSize);
        int readSize = file.gcount();
        file.close();
        if (readSize != fileSize) {
                return false;
        }

        rapidjson::Document document;
        document.Parse<0>(buffer.c_str());

        if (!document.IsObject()) {
                return false;
        }
  
        if (!document.HasMember("proxyPort")) {
                std::cerr << "missing proxy port" << std::endl;
                return false;
        }
        _proxyPort = document["proxyPort"].GetInt();  

        if (!document.HasMember("serverHost")) {
                std::cerr << "missing server host" << std::endl;
                return false;
        }
        _serverHost = document["serverHost"].GetString();

        if (!document.HasMember("serverPort")) {
                std::cerr << "missing server port" << std::endl;
                return false;
        }
        _serverPort = document["serverPort"].GetInt();
  
        if (document.HasMember("logFile")) {
                _logFile = document["logFile"].GetString();
        }
  
        if(document.HasMember("logLevel")) {
                _logLevel = document["logLevel"].GetString();
        }

        return true;
}

unsigned short Config::proxyPort() const {
        return _proxyPort;
}

unsigned short Config::serverPort() const {
        return _serverPort;
}

std::string Config::serverHost() const {
        return _serverHost;
}

std::string Config::logFile() const {
        return _logFile;
}

std::string Config::logLevel() const {
        return _logLevel;
}

bool Config::load(int argc, char **argv) {
        if (!getConfigFilename(argc, argv)) {
                return false;
        }
        return readConfigFile();
}
