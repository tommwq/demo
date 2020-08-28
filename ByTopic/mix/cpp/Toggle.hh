#pragma once

namespace mix {

    class Toggle {
    public:
        bool is_on() const {
            return on;
        }
        bool is_off() const {
            return !on;
        }
        void turn_on() {
            on = true;
        }
        void turn_off() {
            on = false;
        }
    private:
        bool on = false;
    };
}
