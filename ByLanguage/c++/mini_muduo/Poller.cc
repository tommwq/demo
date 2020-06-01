#include "Channel.hh"
#include "Poller.hh"
#include "Timestamp.hh"
#include "Event_loop.hh"

Poller::Poller(Event_loop* loop)
    :owner_loop(loop) {
}

Poller::~Poller() {
}

Timestamp Poller::poll(int timeout, Channel_list *active_channels) {
    int number_of_events = ::poll(&*poll_fds.begin(), poll_fds.size(), timeout);
    Timestamp now(Timestamp::now());

    if (number_of_events > 0) {
        fill_active_channels(number_of_events, active_channels);
    }
    
    return now;
}

void Poller::fill_active_channels(int number_of_events, Channel_list *active_channels) const {
    for (Poll_fd_list::const_iterator it = poll_fds.begin();
         it != poll_fds.end() && number_of_events > 0;
         ++it) {
        if (it->revents == 0) {
            continue;
        }

        --number_of_events;
        Channel_map::const_iterator ch = channels.find(it->fd);
        Channel* channel = ch->second;
        channel->set_revents(it->revents);
        active_channels->push_back(channel);
    }
}

void Poller::update_channel(Channel* channel) {
    assert_in_loop_thread();

    int index = channel->get_index();
    if (index < 0) {
        struct pollfd pfd;
        pfd.fd = channel->get_fd();
        pfd.events = static_cast<short>(channel->get_events());
        pfd.revents = 0;
        poll_fds.push_back(pfd);
        int idx = static_cast<int>(poll_fds.size()) - 1;
        channel->set_index(idx);
        channels[pfd.fd] = channel;
    } else {
        int index = channel->get_index();
        struct pollfd& pfd = poll_fds[index];
        pfd.events = static_cast<short>(channel->get_events());
        pfd.revents = 0;
        if (channel->is_none_event()) {
            pfd.fd = -1;
        }
    }
}

void Poller::assert_in_loop_thread() {
    owner_loop->assert_in_loop_thread();
}

