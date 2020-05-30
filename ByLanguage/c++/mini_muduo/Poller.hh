#pragma once

#include <vector>
#include <map>

#include <poll.h>

#include "Channel.hh"
#include "Timestamp.hh"
#include "Non_copyable.hh"
#include "Common.hh"

class Event_loop;
// 异步事件调度器。
class Poller: Non_copyable {
public:
    Poller(Event_loop *loop);
    ~Poller();

    Timestamp poll(int timeout_milliseconds, Channel_list *active_channels);
    void update_channel(Channel *channel);
    void assert_in_loop_thread();
private:
    typedef std::vector<struct pollfd> Poll_fd_list;
    typedef std::map<int, Channel*> Channel_map;
    
    void fill_active_channels(int number_of_events, Channel_list *active_channel) const;
    
    Event_loop *owner_loop;
    Poll_fd_list poll_fds;
    Channel_map channels;
};

