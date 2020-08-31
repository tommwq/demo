#pragma once

namespace mix {

    class Indicator {
    public:
        bool is_equal() const {
            return indicate == Indicate::equal;
        }
        bool is_less() const {
            return indicate == Indicate::less;
        }
        bool is_greater() const {
            return indicate == Indicate::greater;
        }
        void turn_equal() {
            indicate = Indicate::equal;
        }
        void turn_less() {
            indicate = Indicate::less;
        }
        void turn_greater() {
            indicate = Indicate::greater;
        }
    private:
        enum class Indicate {less, equal, greater};
        Indicate indicate = Indicate::equal;
    };
}