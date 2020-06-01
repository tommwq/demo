#pragma once

// 时间戳。
class Timestamp {
public:
    // 获得当前时间戳。
    static Timestamp now();
    Timestamp();
    Timestamp(double raw_timestamp);
    Timestamp(const Timestamp &);
    double get_raw_timestamp() const;
    bool operator<(const Timestamp& rhs) const;
private:
    double raw_timestamp;
};

Timestamp add_time(const Timestamp &timestamp, double diff);
