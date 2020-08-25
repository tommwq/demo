
void TCPServer::new_connection(int socket_fd, const InetAddress &peer_address) {
    char buffer[32];
    snprintf(buffer, sizeof(buffer), "#%d", next_connection_id);
    ++next_connection_id;
    InetAddress local_address(Socket::get_local_address(socket_fd));
    std::string connection_name = name + buf;
    TCPConnectionPtr connection(new TCPConnection(loop, connection_name, socket_fd, local_address, peer_address));
    connections[connection_name] = connection;
    connection->set_connection_callback(connection_callback);
    connection->set_message_callback(message_callback);
    connection->connect_established();
}
