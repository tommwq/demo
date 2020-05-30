#include <iterator>
#include <utility>

#include <sys/timerfd.h>

#include "Timer_id.hh"
#include "Common.hh"
#include "Timestamp.hh"
#include "Timer.hh"
#include "Timer_queue.hh"
#include "Event_loop.hh"

std::vector<Timer_queue::Entry> Timer_queue::get_expired(Timestamp now) {
    std::vector<Entry> expired;
    Entry sentry = std::make_pair(now, reinterpret_cast<Timer*>(UINTPTR_MAX));
    Timer_list::iterator it = timers.lower_bound(sentry);
    std::copy(timers.begin(), it, std::back_inserter(expired));
    timers.erase(timers.begin(), it);

    return expired;
}

Timer_id Timer_queue::add_timer(const Timer_callback &cb, Timestamp when, double interval) {
    Timer *timer = new Timer(cb, when, interval);
    loop->run_in_loop(std::bind(&Timer_queue::add_timer_in_loop, this, timer));
    return Timer_id(timer);
}

Timer_id Timer_queue::add_timer_in_loop(Timer *timer) {
    bool earliest_changed = insert(timer);
    if (earliest_changed) {
        // TODO
        // reset_timerfd(timerfd, timer->get_expiration());
    }
    return Timer_id(timer);
}

Timer_queue::Timer_queue(Event_loop *loop)
    :loop(loop),
     timerfd(timerfd_create(CLOCK_MONOTONIC, TFD_NONBLOCK | TFD_CLOEXEC)),
     timerfd_channel(loop, timerfd) {
}

Timer_queue::~Timer_queue() {
}

bool Timer_queue::insert(Timer *timer) {
    // TODO
    return false;
}
