#pragma once

class Timer {
public:
    Timer(const Timer_callback &cb, Timestamp when, double interval);
};
