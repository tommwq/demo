
#include "stdafx.h"
#include <cstdlib>
#include <cstdint>
#include <ctime>
#include <chrono>
#include <iostream>
#include <vector>
#include <thread>
#include <string>
#include <mutex>

uint64_t current(){
    std::chrono::system_clock::time_point now = std::chrono::system_clock::now();
    return std::chrono::duration_cast<std::chrono::milliseconds>(now.time_since_epoch()).count();
}

class Cache {
public:
    Cache()
        :update_time_(0), value_(0){
    }
    uint64_t last_update(){
        return update_time_;
    }
    virtual int get(){ return value_; }
    virtual void set(int x){ 
        value_ = x; 
        update_time_ = time(NULL);
    }
private:
    uint64_t update_time_;
    int value_;
};

class LockedCache: public Cache {
public:
    LockedCache(){}
    virtual bool try_get(int &value){
        if (mutex_.try_lock()){
            value = Cache::get();
            mutex_.unlock();
            return true;
        }
        return false;
    }
    virtual int get(){
        int dummy;
        return get(dummy);
    }
    virtual int get(int &update_time){
        mutex_.lock();
        int value = Cache::get();
        update_time = last_update();
        mutex_.unlock();
        return value;
    }
    virtual void set(int x){
        mutex_.lock();
        Cache::set(x);
        mutex_.unlock();
    }
private:
    LockedCache(const LockedCache &);  // = delete
    LockedCache& operator = (const LockedCache &); // = delete
private:
    std::mutex mutex_;
};

class DoubleCache: public Cache {
public:
    // milliseconds
    DoubleCache(size_t cache_timeout = 100)
        :timeout_(cache_timeout){
    }
    virtual int get(){
        uint64_t now = current();
        uint64_t last_update = cache_.last_update();
        int value;
        if (now - last_update > timeout_ && locked_cache_.try_get(value)){
            cache_.set(value);
        }
        return cache_.get();
    }
    virtual void set(int x){
        locked_cache_.set(x);
    }
private:
    DoubleCache(const DoubleCache &); // = delete
    DoubleCache& operator = (const DoubleCache &); // = delete
private:
    Cache cache_;
    LockedCache locked_cache_;
    int timeout_;
};

void getter(DoubleCache *cache, int idx, volatile bool *over){
    while (!*over){
        int x = cache->get();
        printf("%lld: %s %d by %d\n", current(), "get", x, idx);
        if (*over){
            break;
        }
        std::this_thread::sleep_for(std::chrono::milliseconds(std::rand() % 50));
    }
}

void setter(DoubleCache *cache, volatile bool *over){
    static int x = 0;
    static uint64_t start_time(current());

    while (!*over){
        cache->set(++x);
        printf("%lld: %s %d\n", current(), "set", x);

        if (*over){
            break;
        }

        uint64_t now = current();
        auto elapse = now - start_time;
        int remain = 1000 - (elapse % 1000);
        //printf("%lld: sleep for %d\n", current(), remain);
        std::this_thread::sleep_for(std::chrono::milliseconds(remain));
    }
}

int _tmain(int argc, _TCHAR* argv[])
{
    std::srand(std::time(NULL));
    DoubleCache cache;
    bool over = false;

    std::thread setter_t(setter, &cache, &over);
    std::vector<std::thread *> getter_t;

    int size = 20;

    for (int i = 0; i < size; ++i){
        std::thread *t = new std::thread(getter, &cache, i, &over);
        getter_t.push_back(t);
    }

    std::this_thread::sleep_for(std::chrono::minutes(30));
    over = true;

    setter_t.join();
    for (int i = 0; i < size; ++i){
        (getter_t[i])->join();
    }

    return 0;
}

