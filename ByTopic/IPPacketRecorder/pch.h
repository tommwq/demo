
#ifndef PCH_H
#define PCH_H

#define _WINSOCK_DEPRECATED_NO_WARNINGS 

#include <WinSock2.h>
#include <Windows.h>
#include <mstcpip.h>
#include <ws2def.h>
#include <ws2ipdef.h>
#include <ws2tcpip.h>

#include <csignal>
#include <thread>
#include <iostream>
#include <chrono>
#include <string_view>
#include <fstream>

#include "common.h"

#include <concurrent_queue.h>

#pragma comment(lib, "ws2_32.lib")

#endif //PCH_H
