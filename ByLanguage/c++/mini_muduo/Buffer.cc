ssize_t Buffer::read_fd(int fd, int *error_code) {
    char local_buffer[65536];
    struct iovec vec[2];
    const size_t writable = writable_bytes();
    vec[0].iov_base = begin() + writer_index;
    vec[0].iov_len = writable;
    vec[1].iov_base = local_buffer;
    vec[1].iov_len = sizeof(local_buffer);
    const ssize_t n = readv(fd, vec, 2);
    if (n < 0) {
        *error_code = errno;
        return n;
    }

    if (implicit_cast<size_t>(n) <= writable) {
        writer_index += n;
        return n;
    }

    writer_index = buffer.size();
    append(local_buffer, n - writable);
    return n;
}
