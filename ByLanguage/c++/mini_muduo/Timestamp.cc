#include <chrono>

#include "Timestamp.hh"

Timestamp Timestamp::now() {
    Timestamp t;
    return t;
}

Timestamp::Timestamp()
    :raw_timestamp(std::chrono::duration_cast<std::chrono::seconds>(std::chrono::system_clock::now().time_since_epoch()).count()) {
}

Timestamp::Timestamp(const Timestamp &rhs)
    :raw_timestamp(rhs.raw_timestamp) {
}

Timestamp::Timestamp(double raw_timestamp)
    :raw_timestamp(raw_timestamp) {
}

double Timestamp::get_raw_timestamp() const {
    return raw_timestamp;
}

Timestamp add_time(const Timestamp &timestamp, double diff) {
    Timestamp result(timestamp.get_raw_timestamp() + diff);
    return result;
}

bool Timestamp::operator<(const Timestamp& rhs) const {
    return raw_timestamp - rhs.raw_timestamp;
}
