#pragma once

#include <functional>
#include "Non_copyable.hh"
#include "Common.hh"

class Event_loop;

// 封装异步事件。
class Channel: Non_copyable {
public:
    typedef std::function<void()> Event_callback;

    Channel(Event_loop *loop, int fd);

    void handle_event();
    // 异步事件句柄。
    void set_read_callback(const Event_callback& cb);
    void set_write_callback(const Event_callback& cb);
    void set_error_callback(const Event_callback& cb);

    int get_fd() const;
    int get_events() const;
    void set_revents(int revt);
    bool is_none_event() const;
    void enable_reading();
    int get_index() const;
    void set_index(int index);
    Event_loop* owner_loop();
private:
    void update();

    static const int NoneEvent;
    static const int ReadEvent;
    static const int WriteEvent;

    Event_loop* loop;
    const int fd;
    int events;  // IO事件
    int revents;  // 当前活动事件
    int index;  // 给Poller使用

    Event_callback read_callback;
    Event_callback write_callback;
    Event_callback error_callback;
};


inline void Channel::set_read_callback(const Event_callback& cb) {
    read_callback = cb;
}

inline void Channel::set_write_callback(const Event_callback& cb) {
    write_callback = cb;
}

inline void Channel::set_error_callback(const Event_callback& cb) {
    error_callback = cb;
}

inline int Channel::get_fd() const {
    return fd;
}

inline int Channel::get_events() const {
    return events;
}
inline void Channel::set_revents(int revt) {
    revents = revt;
}
inline bool Channel::is_none_event() const {
    return events == NoneEvent;
}
inline void Channel::enable_reading() {
    events |= ReadEvent;
    update();
}
inline int Channel::get_index() const {
    return index;
}
inline void Channel::set_index(int index) {
    this->index = index;
}

inline Event_loop* Channel::owner_loop() {
    return loop;
}
