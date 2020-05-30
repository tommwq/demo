#include "Event_loop.hh"
#include "Acceptor.hh"

Acceptor::Acceptor(Event_loop *loop, const InetAddress &listen_address)
    :loop(loop),
     listen_socket(Socket::create_nonblocking_socket()),
     listen_channel(loop, listen_socket.get_fd()),
     listenning(false) {
    listen_socket.set_reuse_address(true);
    listen_socket.bind_address(listen_address);
    listen_channel.set_read_callback(std::bind(handle_read, this));
}

void Acceptor::listen() {
    listenning = true;
    listen_socket.listen();
    listen_channel.enable_reading();
}

void Acceptor::set_new_connection_callback(const New_connection_callback &cb) {
    new_connection_callback = cb;
}

bool Acceptor::is_listenning() const {
    return listenning;
}

void Acceptor::handle_read() {
    InetAddress peer_address(0);
    int connection_fd = listen_socket.accept(&peer_address);
    if (connection_fd < 0) {
        return;
    }

    if (!new_connection_callback) {
        Socket::close(connection_fd);
        return;
    }

    new_connection_callback(connection_fd, peer_address);
}
