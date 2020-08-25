#pragma once

#include <thread>
#include <memory>
#include <mutex>
#include <set>
#include <functional>

#include "Non_copyable.hh"
#include "Debug.hh"
#include "Common.hh"
#include "Poller.hh"
#include "Timer_id.hh"
#include "Timer_queue.hh"
#include "Timestamp.hh"

class Channel;
// 事件循环。
class Event_loop: Non_copyable {
public:
    Event_loop();
    ~Event_loop();

    static Event_loop* get_local_event_loop();

    // 循环。
    void loop();
    void assert_in_loop_thread();
    bool is_in_loop_thread() const;
    void quit();
    void update_channel(Channel* channel);
    Timer_id run_at(const Timestamp &time, const Timer_callback &cb);
    Timer_id run_after(double delay, const Timer_callback &cb);
    Timer_id run_every(double interval, const Timer_callback &cb);
    void run_in_loop(const std::function<void()> &cb);
    void queue_in_loop(const std::function<void()> &cb);
    void wakeup();
private:
    void abort();
    bool is_in_loop;
    bool is_quitted;
    const std::thread::id thread_id;
    std::shared_ptr<Poller> poller;
    Channel_list active_channels;
    void handle_read();
    void do_pending_functors();
    bool calling_pending_functors;
    int wakeup_fd;
    std::shared_ptr<Channel> wakeup_channel;
    std::mutex mutex;
    std::vector<std::function<void()>> pending_functors;
    std::shared_ptr<Timer_queue> timer_queue;
};

inline void Event_loop::assert_in_loop_thread() {
    if (!is_in_loop_thread()) {
        abort();
    }
}

inline bool Event_loop::is_in_loop_thread() const {
    return thread_id == std::this_thread::get_id();
}

inline void Event_loop::abort() {
    std::abort();
}

inline void Event_loop::quit() {
    is_quitted = true;
    if (!is_in_loop_thread()) {
        wakeup();
    }
}

inline void Event_loop::update_channel(Channel* channel) {
    poller->update_channel(channel);
}

inline Timer_id Event_loop::run_at(const Timestamp &time, const Timer_callback &cb) {
    return timer_queue->add_timer(cb, time, 0);
}

inline Timer_id Event_loop::run_after(double delay, const Timer_callback &cb) {
    Timestamp time(add_time(Timestamp::now(), delay));
    return run_at(time, cb);
}

inline Timer_id Event_loop::run_every(double interval, const Timer_callback &cb) {
    Timestamp time(add_time(Timestamp::now(), interval));
    return timer_queue->add_timer(cb, time, interval);
}

inline void Event_loop::run_in_loop(const std::function<void()> &cb) {
    if (is_in_loop_thread()) {
        cb();
        return;
    }

    queue_in_loop(cb);
}

inline void Event_loop::queue_in_loop(const std::function<void()> &cb) {
    {
        std::lock_guard<std::mutex> guard(mutex);
        pending_functors.push_back(cb);
    }

    if (!is_in_loop_thread() || calling_pending_functors) {
        wakeup();
    }
}

