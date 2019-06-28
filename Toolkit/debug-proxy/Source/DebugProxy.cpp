/**
 * debugProxy.cpp
 *
 * 实现DebugProxy类。
 *
 * 建立日期：2015-11-24
 * 最后编辑：2019年06月28日
 */

#include "PCH.hpp"
#include "DebugProxy.hpp"

DebugProxy& DebugProxy::setProxyPort(unsigned short port) {
    _proxyPort = port;
    return *this;
}

DebugProxy& DebugProxy::setServerPort(unsigned short port) {
    _serverPort = port;
    return *this;
}

DebugProxy& DebugProxy::setServerHost(std::string const &host) {
    _serverHost = host;
    return *this;
}

unsigned short DebugProxy::getProxyPort() const {
    return _proxyPort;
}

unsigned short DebugProxy::getServerPort() const {
    return _serverPort;
}

std::string DebugProxy::getServerHost() const {
    return _serverHost;
}

DebugProxy::DebugProxy()
    :_listenSocket(INVALID_SOCKET),
     _logger(Logger::logger()),
     _running(false),
     _iocp(INVALID_HANDLE_VALUE) {}

DebugProxy::~DebugProxy() {}

void DebugProxy::startProxy() {
    if (!initialize()) {
        return;
    }
          
    if (!createCompletionPort()) {
        return;
    }

    if (!createListenPort()) {
        return;
    }

    if (!addToCompletionPort(_listenSocket, nullptr)) {
        return;
    }

    _running = true;
    for (int i = 0; i < _threadNumber; i++) {
        std::thread *t = new std::thread(DebugProxy::process, this);
        _threads[i] = t;
    }
  
    enterAcceptCycle();
    cleanup();
}

bool DebugProxy::initializeThread() {
    WORD wsaVersion = MAKEWORD(2, 0);
    WSADATA wsaData;
    int ret = WSAStartup(wsaVersion, &wsaData);
    if (ret != 0) {
        _logger.write(Logger::LogLevel::info, "Fail to initialize windows socket library.");
        return false;
    }
    return true;
}

bool DebugProxy::initialize() {
    if (!initializeThread()) {
        return false;
    }

    for (int i = 0; i < _threadNumber; i++) {
        _threads[i] = nullptr;
    }

    return true;
}

bool DebugProxy::addToCompletionPort(SOCKET socket, void *key) {
    HANDLE handle = ::CreateIoCompletionPort(reinterpret_cast<HANDLE>(socket), 
                                             _iocp, reinterpret_cast<ULONG_PTR>(key), 0);

    if (handle == nullptr) {
        _logger.write(Logger::LogLevel::info, "Fail to add socket to completion port.");
        return false;
    }
    return true;
}

bool DebugProxy::createListenPort() {
    _listenSocket = ::WSASocket(AF_INET, SOCK_STREAM, 0, NULL, 0, WSA_FLAG_OVERLAPPED);
    if (_listenSocket == INVALID_SOCKET) {
        _logger.write(Logger::LogLevel::info, "Fail to create socket.");
        return false;
    }
    struct sockaddr_in address;
    address.sin_family = AF_INET;
    address.sin_port = ::htons(_proxyPort);
    address.sin_addr.s_addr = INADDR_ANY;
    int ret = bind(_listenSocket, (struct sockaddr*)&address, sizeof(address));
    if (ret != 0) {
        _logger.write(Logger::LogLevel::info, "Fail to bind socket.");
        return false;
    }
  
    ret = listen(_listenSocket, 10);
    if (ret != 0) {
        _logger.write(Logger::LogLevel::info, "Fail to listen socket.");
        return false;
    }
  
    return true;
}

bool DebugProxy::createCompletionPort() {
    _iocp = ::CreateIoCompletionPort(INVALID_HANDLE_VALUE, NULL, 0, _threadNumber);
    if (_iocp == INVALID_HANDLE_VALUE) {
        _logger.write(Logger::LogLevel::info, "Fail to create io completion port.");
        return false;
    }
    return true;
}

void DebugProxy::cleanup() {
    for (auto i = _threads.begin(); i != _threads.end(); i++) {
        (*i)->join();
        delete *i;
    }

    for (int i = 0; i < _threadNumber; i++) {
        _threads[i] = nullptr;
    }

    ::CloseHandle(_iocp);
    _iocp = INVALID_HANDLE_VALUE;
    ::closesocket(_listenSocket);
    _listenSocket = INVALID_SOCKET;
    // todo release _connections
}

void DebugProxy::process(DebugProxy *self) {
    self->_logger.write(Logger::LogLevel::info, "Start process thread.");
    if (!self->initializeThread()) {
        return;
    }
  
    BOOL ret;
    Connection *connection = nullptr;
    OVERLAPPED *overlapped = nullptr;
    DWORD size;
    DWORD flags;
    while (true) {
        ret = ::GetQueuedCompletionStatus(self->_iocp,
                                          &size, 
                                          reinterpret_cast<PULONG_PTR>(&connection), 
                                          &overlapped, INFINITE);                
        if (ret == FALSE) {
            self->_logger.write(Logger::LogLevel::debug, "Fail to invoke GetQueuedCompletionStatus");
            self->closeConnection(connection);
            continue;
        }
        ret = ::WSAGetOverlappedResult(connection->_socket, 
                                       &connection->_receiveBuffer._overlapped, 
                                       &size, TRUE, &flags);
        if (ret != TRUE) {
            self->_logger.write(Logger::LogLevel::debug, "Fail to invoke WSAGetOverlappedResult.");
            self->closeConnection(connection);
            continue;
        }
        connection->_receiveBuffer._wsabuf.len = size;

        if (size == 0) {
            self->closeConnection(connection);
            continue;
        }

        char const *buffer = connection->_receiveBuffer._wsabuf.buf;
        std::string tag;
        if (connection->peer() != nullptr) {
            tag = connection->peer()->tag();
        }
        self->recordPackage(tag, buffer, size);
        self->sendToPeer(connection);
        self->receive(connection);
    }
    self->_logger.write(Logger::LogLevel::info, "Stop process thread.");
}

void DebugProxy::closeConnection(Connection *connection) {
    std::ostringstream os;
    os << connection->tag() << " closed";
    _logger.write(Logger::LogLevel::info, os.str());

    connection->close();
    if (!connection->peer()->closed()) {
        connection->peer()->close();
        return;
    }
        
    delete connection->peer();
    delete connection;
}

void DebugProxy::enterAcceptCycle() {
    struct sockaddr_in address;
    int addressSize = sizeof(address);
    std::array<char, 4096> buffer;
    WSABUF wsaBuffer;
    wsaBuffer.len = buffer.size();
    wsaBuffer.buf = buffer.data();
  
    while (_running) {
        SOCKET clientSocket = WSAAccept(_listenSocket, (struct sockaddr*) &address, 
                                        &addressSize, NULL, NULL);                
        if (clientSocket == INVALID_SOCKET && GetLastError() == WSAEWOULDBLOCK) {
            _logger.write(Logger::LogLevel::debug, "Accept pending.");
            continue;
        }
        if (clientSocket == INVALID_SOCKET) {
            _logger.write(Logger::LogLevel::info, "Fail to invoke WSAAccept.");
            continue;
        }
        newConnection(clientSocket);
    }
}

void DebugProxy::newConnection(SOCKET clientSocket) {
    _logger.write(Logger::LogLevel::debug, "New connection.");
    static int connectionPairId = 0;

    connectionPairId++;
    std::ostringstream os;
    os << "Client " << connectionPairId;
    Connection *clientConnection = new Connection(os.str());
    os.str("");
    os << "Server " << connectionPairId;
    Connection *forwardConnection = new Connection(os.str());
    clientConnection->peer(forwardConnection);
    forwardConnection->peer(clientConnection);
        
    SOCKET forwardSocket = INVALID_SOCKET;
    do {
        setSocketNonBlock(clientSocket);
        if (!addToCompletionPort(clientSocket, clientConnection)) {
            _logger.write(Logger::LogLevel::info, "Fail to add client socket to completion port.");
            break;
        }
        if ((forwardSocket = newForwardSocket()) == INVALID_SOCKET) {
            _logger.write(Logger::LogLevel::info, "Fail to create forward socket.");
            break;
        }
        if (!addToCompletionPort(forwardSocket, forwardConnection)) {
            _logger.write(Logger::LogLevel::info, "Fail to add forward socket to completion port.");
            break;
        }

        forwardConnection->_socket = forwardSocket;
        forwardConnection->_peerSocket = clientSocket;
        clientConnection->_socket = clientSocket;
        clientConnection->_peerSocket = forwardSocket;

        if (!receive(clientConnection)) {
            break;
        }
        if (!receive(forwardConnection)) {
            break;
        }
        return;
    } while (false);
    ::closesocket(clientSocket);
    ::closesocket(forwardSocket);
    delete clientConnection;
    delete forwardConnection;
}

bool DebugProxy::sendToPeer(Connection *connection) {
    DWORD size;
    int ret = ::WSASend(connection->_peerSocket, &connection->_receiveBuffer._wsabuf, 1, 
                        &size, 0, NULL, NULL);
    return (ret == TRUE);
}

bool DebugProxy::receive(Connection *connection) {
    DWORD size;
    DWORD flags = 0;
    connection->_receiveBuffer.reset();
    int ret = ::WSARecv(connection->_socket, &connection->_receiveBuffer._wsabuf, 1, 
                        &size, &flags, &connection->_receiveBuffer._overlapped, NULL);
    if (ret != 0 && ::WSAGetLastError() != WSA_IO_PENDING) {
        _logger.write(Logger::LogLevel::info, "Fail to invoke WSARecv.");
        return false;
    }
    return true;
}

DebugProxy::SocketBuffer::SocketBuffer() {
    reset();
}

void DebugProxy::SocketBuffer::reset() {
    _wsabuf.len = _data.size();
    _wsabuf.buf = _data.data();
    ::ZeroMemory(&_overlapped, sizeof(_overlapped));
}

SOCKET DebugProxy::newForwardSocket() {
    SOCKET socket = ::WSASocket(AF_INET, SOCK_STREAM, 0, NULL, 0, WSA_FLAG_OVERLAPPED);
    do {
        if (socket == INVALID_SOCKET) {
            break;
        }
        struct sockaddr_in address;
        address.sin_family = AF_INET;
        address.sin_port = ::htons(_serverPort);
        address.sin_addr.s_addr = ::inet_addr(_serverHost.c_str());
        int ret = ::WSAConnect(socket, (struct sockaddr*) &address, sizeof(address), 
                               NULL, NULL, NULL, NULL);
        if (ret != 0) {
            _logger.write(Logger::LogLevel::info, "Fail to connect to forward server.");
            break;
        }
        setSocketNonBlock(socket);
        return socket;
    } while (false);
    ::closesocket(socket);
    return INVALID_SOCKET;
}

void DebugProxy::setSocketNonBlock(SOCKET socket) const {
    DWORD option = 1;
    DWORD byte = 0;
    ::WSAIoctl(socket, FIONBIO, &option, sizeof(option), &option, sizeof(option), &byte, NULL, NULL);
}


void DebugProxy::recordPackage(std::string const& tag, char const *buffer, size_t size) {
    if (buffer == nullptr || size == 0) {
        return;
    }

    std::ostringstream stream;
    int const width = 16;
    int row = size / width;
    if (size % width != 0) {
        row++;
    }
    int offset = 0;
    int half = width / 2;
  
    if (tag.empty()) {
        stream << std::endl;
    } else {
        stream << "Send to " << tag << std::endl;
    }
        
    for (int i = 0; i < row; i++) {
        stream << std::setbase(16);
        for (int j = 0; j < width; j++) {
            offset = i * width + j;
            if (offset >= size) {
                stream << "   ";
                continue;
            }
            if (j == half) {
                stream << " ";
            }
            stream << std::setw(2) << std::setfill('0') 
                   << (static_cast<int>(buffer[offset]) & 0xff) << " ";
        }
        stream << "  ";
        for (int j = 0; j < width; j++) {
            offset = i * width + j;
            if (offset >= size) {
                break;
            }
            char c = buffer[offset];
            if (!std::isprint(c)) {
                c = '.';
            }
            stream << c;
        }
        stream << std::endl;
    }

    _logger.write(Logger::LogLevel::info, stream.str());
}
