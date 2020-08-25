class TCPServer: Non_copyable {
public:
    TCPServer(Event_loop *loop, const InetAddress &listen_address);
    ~TCPServer();

    void start();
    void set_connection_callback(const Connection_callback &cb);
    void set_message_callback(const Message_callback &cb);
private:
    void new_connection(int socket_fd, const InetAddress &peer_address);
    typedef std::map<std::string, TCPConnectionPtr> ConnectionTable;
    Event_loop *loop;
    const std::string name;
    std::shared_ptr<Acceptor> acceptor;
    Connection_callback connection_callback;
    Message_callback message_callback;
    bool started;
    int next_connection_id;
    ConnectionTable connections;
};
