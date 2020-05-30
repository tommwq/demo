DebugProxy使用说明

* DebugProxy简介

DebugProxy会转发并记录数据包，用户调试服务通信协议。

* DebugProxy配置

DebugProxy配置文件是一个JSON文件，格式如下：

    {
        "proxyPort": 1000,
        "serverHost": "172.24.181.125",
        "serverPort": 9000,
        "logFile": "DebugProxy.log",
        "logLevel": "debug"
    }

其中，serverHost和serverPort是转发的服务器地址和端口，proxyPort是监听端口。logFile是日志文件。
