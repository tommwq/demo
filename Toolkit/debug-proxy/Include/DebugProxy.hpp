/**
 * DebugProxy.hpp
 *
 * 定义DebugProxy类。DebugProxy类是一个代理，将转发的数据记录下来，用来分析通信协议。
 *
 * 建立日期：2015-11-24
 * 最后编辑：2019年06月28日
 */

#pragma once

class DebugProxy {
public:
    DebugProxy();
    ~DebugProxy();
    DebugProxy& operator=(DebugProxy const &rhs) = delete;
    DebugProxy(DebugProxy const &rhs) = delete;
    DebugProxy& setProxyPort(unsigned short port);
    DebugProxy& setServerPort(unsigned short port);
    DebugProxy& setServerHost(std::string const &host);
    void startProxy();
    unsigned short getProxyPort() const;
    unsigned short getServerPort() const;
    std::string getServerHost() const;
    
private:
    class SocketBuffer {
    public:
        SocketBuffer();
        void reset();
    public:
        WSABUF _wsabuf;
        WSAOVERLAPPED _overlapped;
    private:
        std::array<char, 4194304> _data; // 4M 
    };

    class Connection {
    public:
        Connection()
            :_closed(false) {
            _sendBuffer.reset();
            _receiveBuffer.reset();
        }
        Connection(std::string tag)
            :_tag(tag), _closed(false) {
            _sendBuffer.reset();
            _receiveBuffer.reset();
        }
        std::string const& tag() const {
            return _tag;
        }
        Connection* peer() {
            return _peer;
        }
        Connection& peer(Connection *conn) {
            _peer = conn;
            return *this;
        }
        void close() {
            ::closesocket(_socket);
            _closed = true;
            _sendBuffer.reset();
            _receiveBuffer.reset();
        }
        bool closed() const {
            return _closed;
        }
    public:
        SOCKET _peerSocket;
        SOCKET _socket;
        SocketBuffer _sendBuffer;
        SocketBuffer _receiveBuffer;
        std::string _tag;
        Connection *_peer;
    private:
        std::array<char, 4096> _data;
        bool _closed;
    };
private:
    bool initialize();
    bool initializeThread();
    bool createCompletionPort();
    bool createListenPort();
    bool addToCompletionPort(SOCKET socket, void *key);
    void cleanup();
    void enterAcceptCycle();
    static void process(DebugProxy *self);
    void releaseConnection(SOCKET clientSocket);
    void newConnection(SOCKET clientSocket);
    SOCKET newForwardSocket();
    void setSocketNonBlock(SOCKET socket) const;
    bool receive(Connection *connection);
    bool sendToPeer(Connection *connection);
    void recordPackage(std::string const &tag, char const *buffer, size_t size);
    void closeConnection(Connection *conn);
private:
    static int const _threadNumber = 4;
    unsigned short _proxyPort;
    unsigned short _serverPort;
    std::string _serverHost;
    Logger &_logger;
    SOCKET _listenSocket;
    HANDLE _iocp;
    std::array<std::thread*, _threadNumber> _threads;
    bool _running;
    std::map<SOCKET, Connection*> _connections;
};

