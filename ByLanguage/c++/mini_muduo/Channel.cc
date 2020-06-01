#include <poll.h>

#include "Debug.hh"
#include "Channel.hh"
#include "Event_loop.hh"

const int Channel::NoneEvent = 0;
const int Channel::ReadEvent = POLLIN | POLLPRI;
const int Channel::WriteEvent = POLLOUT;

Channel::Channel(Event_loop *loop, int fd)
    :loop(loop),
     fd(fd),
     events(0),
     revents(0),
     index(-1) {
}

void Channel::update() {
    loop->update_channel(this);
}

void Channel::handle_event() {
    if ((revents & (POLLERR | POLLNVAL)) && error_callback) {
        error_callback();
    }
    
    if ((revents & (POLLIN | POLLPRI | POLLRDHUP)) && read_callback) {
        read_callback();
    }
    
    if ((revents & POLLOUT) && write_callback) {
        write_callback();
    }
}
