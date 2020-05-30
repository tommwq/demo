typedef std::shared_ptr<TCPConnection> TCPConnectoinPtr;

class TCPConnection: Non_copyable, std::enable_shared_from_this<TCPConnection> {
public:
private:
    enum State {Connecting, Connected};
    void set_state(State s);
    void handle_read();
    Event_loop *loop;
    std::string name;
    State state;

    std::shared_ptr<Socket> socket;
    std::shared_ptr<Channel> channel;
    InetAddress local_address;
    InetAddress peer_address;
    ConnectionCallback connection_callback;
    MessageCallback message_callback;
};
