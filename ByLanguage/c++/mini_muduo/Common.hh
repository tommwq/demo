#pragma once

#include <vector>
#include <functional>

class Channel;
typedef std::vector<Channel*> Channel_list;
typedef std::function<void()> Timer_callback;
