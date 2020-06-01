#pragma once

// 为对象赋予值语义。
class Non_copyable {
protected:
    Non_copyable(const Non_copyable&) = delete;
    Non_copyable& operator=(const Non_copyable&) = delete;
    Non_copyable() = default;
    ~Non_copyable() = default;
};
