#pragma once

#include <thread>

class Event_loop;
class Event_loop_thread {
public:
    Event_loop* start_loop();
    static void thread_func(Event_loop_thread *self);
private:
    std::condition_variable condition;
    std::mutex mutex;
    std::thread *thread;
    Event_loop* event_loop;
};
