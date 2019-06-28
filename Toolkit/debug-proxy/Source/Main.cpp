/**
 * main.cpp
 * DebugProxy程序入口。
 *
 * 建立日期：2015-11-24
 * 最后修改：2016-09-30
 */

#include "PCH.hpp"
#include "Config.hpp"
#include "DebugProxy.hpp"

#pragma comment(lib, "ws2_32.lib")

[[noreturn]] void showUsageThenExit(char const *programName);

int main(int argc, char **argv) {
        if (argc == 1) {
                showUsageThenExit(argv[0]);
        }

        Logger &logger(Logger::logger());
        logger.setLogLevel(Logger::LogLevel::debug);

        logger.write(Logger::LogLevel::info, "Loading config.");
        Config config;
        if (!config.load(argc, argv)) {
                std::cerr <<"ERROR: fail to load config." << std::endl;
                std::exit(-1);
        }
        logger.setLogFile(config.logFile());
        logger.setLogLevel(logger.levelFromString(config.logLevel()));
        logger.write(Logger::LogLevel::info, "Config loaded.");

        DebugProxy proxy;
        proxy.setProxyPort(config.proxyPort())
                .setServerPort(config.serverPort())
                .setServerHost(config.serverHost());

        logger.write(Logger::LogLevel::info, "Starting proxy.");
        proxy.startProxy();
        logger.write(Logger::LogLevel::info, "Proxy stopped.");
        return 0;
}

[[noreturn]] void showUsageThenExit(char const *programName) {
        std::cout << "Usage: " << std::endl
                  << programName << " --config config.json" << std::endl
                  << "sample of config.json: " << std::endl
                  << "{\n"
                "    \"proxyPort\": 1010,\n"
                "    \"serverHost\": \"172.24.181.125\",\n"
                "    \"serverPort\": \"9000\"\n"
                "    \"logFile\": \"debugProxy.log\",\n"
                "    \"logLevel\": \"debug\"\n"
                "}\n";

        std::exit(0);
}
