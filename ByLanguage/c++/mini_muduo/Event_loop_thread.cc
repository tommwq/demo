#include <condition_variable>
#include <mutex>

#include "Event_loop_thread.hh"
#include "Event_loop.hh"

Event_loop* Event_loop_thread::start_loop() {
    thread = new std::thread(Event_loop_thread::thread_func, this);

    {
        std::unique_lock<std::mutex> lock(mutex);
        while (event_loop == nullptr) {
            condition.wait(lock);
        }
    }

    return event_loop;
}

void Event_loop_thread::thread_func(Event_loop_thread *self) {
    Event_loop loop;
    {
        std::lock_guard<std::mutex> guard(self->mutex);
        self->event_loop = &loop;
        self->condition.notify_one();
    }

    loop.loop();
}
