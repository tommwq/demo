
class Connector: Non_copyable {
public:
    typedef std::function<void(int)> New_connection_callback;

    Connector(Event_loop *loop, const InetAddress &server_address);
    ~Connector();
};
