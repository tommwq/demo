#pragma once

class Socket {
public:
    static int create_nonblocking_socket() {
        int socket_fd = ::socket(AF_INET, SOCK_STREAM | SOCK_NONBLOCK | SOCK_CLOEXEC, IPPROTO_TCP);
        if (socket_fd < 0) {
            std::abort();
        }

        return socket_fd;
    }

    static void close(int socket) {
        ::close(socket);
    }

    static int accept(int socket_fd, struct sockaddr_in *address) {
        socklen_t address_length = sizeof(*address);
        int connection_fd = ::accept4(socket_fd,
                                      sockaddr_cast(addr),
                                      &address_length,
                                      SOCK_NONBLOCK | SOCK_CLOEXEC);
        if (connection_fd < 0) {
            int error_reason = errno;
            // TODO 处理错误
        }

        return connection_fd;
    }
};
