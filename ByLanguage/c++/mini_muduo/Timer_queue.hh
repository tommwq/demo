#pragma once

#include <vector>
#include <utility>
#include <set>
#include "Non_copyable.hh"
#include "Timestamp.hh"
#include "Common.hh"
#include "Channel.hh"

class Timer;
class Event_loop;
class Timer_queue: Non_copyable {
public:
    Timer_queue(Event_loop* loop);
    ~Timer_queue();
    Timer_id add_timer(const Timer_callback& cb, Timestamp when, double interval);    
    // void cancel(Timer_id timer_id);
private:
    typedef std::pair<Timestamp, Timer*> Entry;
    typedef std::set<Entry> Timer_list;
    Timer_id add_timer_in_loop(Timer* timer);
    void handle_read();
    std::vector<Entry> get_expired(Timestamp now);
    void reset(const std::vector<Entry>& expired, Timestamp now);
    bool insert(Timer* timer);
    Event_loop* loop;
    const int timerfd;
    Channel timerfd_channel;
    Timer_list timers;
};
