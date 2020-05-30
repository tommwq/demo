#pragma once

#include <functional>
#include "Non_copyable.hh"
#include 

class Event_loop;
class Acceptor: Non_copyable {
public:
    typedef std::function<void(int,const InetAddress&)> New_connection_callback;

    Acceptor(Event_loop *loop, const InetAddress &listen_address);
    void set_new_connection_callback(const New_connection_callback &cb);
    bool is_listenning() const;
    void listen();
private:
    void handle_read();
    Event_loop *loop;
    Socket listen_socket;
    Channel listen_channel;
    New_connection_callback new_connection_callback;
    bool listenning;
};
