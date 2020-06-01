
void TCPConnection::handle_read() {
    char buffer[65536];
    ssize_t size = ::read(channel->get_fd(), buffer, sizeof(buffer));
    message_callback(shared_from_this(), buffer, size);
}


