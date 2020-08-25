#include <cassert>
#include <cstdlib>
#include <thread>
#include <vector>
#include <mutex>

#include "Non_copyable.hh"
#include "Event_loop.hh"
#include "Poller.hh"
#include "Channel.hh"
#include "Common.hh"

thread_local Event_loop* loop_in_this_thread = nullptr;

Event_loop::Event_loop()
    :is_in_loop(false),
     thread_id(std::this_thread::get_id()),
     poller(new Poller(this)),
     timer_queue(new Timer_queue(this)) {
    if (loop_in_this_thread) {
        std::abort();
    } 

    loop_in_this_thread = this;
}

Event_loop::~Event_loop() {
    assert(!is_in_loop);
    loop_in_this_thread = NULL;
    poller.reset();
}

Event_loop* Event_loop::get_local_event_loop() {
    return loop_in_this_thread;
}

void Event_loop::loop() {
    assert(!is_in_loop);
    assert_in_loop_thread();
    is_in_loop = true;
    is_quitted = false;

    while (!is_quitted) {
        active_channels.clear();
        poller->poll(100, &active_channels);
        for (Channel_list::iterator it = active_channels.begin();
             it != active_channels.end(); ++it) {
            (*it)->handle_event();
        }
        do_pending_functors();
    }

    is_in_loop = false;
}

void Event_loop::do_pending_functors() {
    std::vector<std::function<void()>> functors;

    {
        std::lock_guard<std::mutex> guard(mutex);
        functors.swap(pending_functors);
    }

    for (size_t i = 0; i < functors.size(); ++i) {
        functors[i]();
    }

    calling_pending_functors = false;
}

void Event_loop::wakeup() {
}


