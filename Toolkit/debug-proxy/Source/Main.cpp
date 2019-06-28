/**
 * main.cpp
 * DebugProxy程序入口。
 *
 * 建立日期：2015-11-24
 * 最后修改：2019年06月28日
 */

#include "PCH.hpp"
#include "Config.hpp"
#include "DebugProxy.hpp"

#pragma comment(lib, "ws2_32.lib")
#pragma comment(lib, "Mswsock.lib")
    
using namespace std::chrono_literals;

[[noreturn]] void showUsageThenExit(char const *programName);

DebugProxy proxy;
Logger &logger(Logger::logger());

BOOL WINAPI userStopProxy(DWORD ctrlType) {
    proxy.stopProxy();
    logger.write(Logger::LogLevel::info, "ctrl c");
    return TRUE;
}

int main(int argc, char **argv) {
        if (argc == 1) {
                showUsageThenExit(argv[0]);
        }

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

        proxy.setProxyPort(config.proxyPort())
                .setServerPort(config.serverPort())
                .setServerHost(config.serverHost());

        SetConsoleCtrlHandler(userStopProxy, TRUE);
        
        logger.write(Logger::LogLevel::info, "Starting proxy.");
        proxy.startProxy();

        while (proxy.isRunning()) {
            std::this_thread::sleep_for(1s);
        }
        
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
