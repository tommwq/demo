#include <iostream>

#include <unistd.h>
#include <strings.h>
#include <sys/timerfd.h>

#include "Debug.hh"
#include "Channel.hh"
#include "Event_loop.hh"

Event_loop* global_loop;

void timeout() {
    std::cout << "timeout" << std::endl;
    global_loop->quit();
}

int main() {
    Event_loop loop;
    global_loop = &loop;
    
    int timerfd = ::timerfd_create(CLOCK_MONOTONIC, TFD_NONBLOCK | TFD_CLOEXEC);
    Channel channel(&loop, timerfd);
    channel.set_read_callback(timeout);
    channel.enable_reading();

    struct itimerspec howlong;
    bzero(&howlong, sizeof(howlong));
    howlong.it_value.tv_sec = 5;
    ::timerfd_settime(timerfd, 0, &howlong, NULL);

    loop.loop();
    
    ::close(timerfd);
}
