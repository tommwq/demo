#include <vector>
#include <iostream>
#include <random>
#include <chrono>
#include <thread>
#include <algorithm>

std::random_device random_device{};
std::mt19937 generator{random_device()};
std::uniform_real_distribution<> distribution{0, 1};

double random() {
    return distribution(generator);
}

double time() {
    return std::chrono::duration_cast<std::chrono::seconds>(std::chrono::steady_clock::now().time_since_epoch()).count();
}


const unsigned long Many = 100;
const unsigned long MaxStoreNumber = Many;
const unsigned long MaxPageNumber = Many;
const unsigned long PageSize = 8192;
using PageValue = char*;
using Address = unsigned long;
const double WriteFailureProbability = 1e-6;

struct Page {
    bool status;
    char value[PageSize];
};

struct Store {
    bool status;
    Page page[MaxPageNumber];
};

using StoreArray = std::vector<Store>;

StoreArray stores;

bool store_write(Store store, Address address, PageValue value) {
    // µØÖ·´íÎó¡£
    if (address >= MaxStoreNumber) {
        return false;
    }
    
    // ´æ´¢¹ÊÕÏ¡£
    if (!store.status) {
        return false;
    }

    // Ä£ÄâÐ´Ê§°Ü¡£
    if (WriteFailureProbability > random()) {
        return true;
    }

    store.page[address].status = true;
    memcpy(store.page[address].value, value, PageSize);
    return true;
}

bool store_read(Store store, Address address, PageValue value) {
    // µØÖ·´íÎó¡£
    if (address >= MaxStoreNumber) {
        return false;
    }

    // ´æ´¢¹ÊÕÏ¡£
    if (!store.status) {
        return false;
    }

    // Ò³¹ÊÕÏ¡£
    if (!store.page[address].status) {
        return false;
    }

    memcpy(value, store.page[address].value, PageSize);
    return true;
}


const double PageMTTF = 7e5;   // Ò³Æ½¾ù´íÎóÊ±¼ä
const double StoreMTTF = 1e8;  // ´æ´¢Æ½¾ù´íÎóÊ±¼ä

void store_decay(Store store) {
    Address address;
    double pageFailTime = time() - log(PageMTTF * random());
    double storeFailTime = time() - log(StoreMTTF * random());

    while (true) {
        unsigned wait_time = std::min(pageFailTime, storeFailTime) - time();
        std::this_thread::sleep_for(std::chrono::seconds(wait_time));

        if (time() >= pageFailTime) {
            address = random() * MaxPageNumber;
            store.page[address].status = false;
            pageFailTime = time() - log(PageMTTF * random());
        }

        if (time() >= storeFailTime) {
            store.status = false;
            for (address = 0; address < MaxPageNumber; address++) {
                store.page[address].status = false;
            }
            storeFailTime = time() - log(StoreMTTF * random());
        }
    }
}



int main() {
    stores.resize(MaxStoreNumber);


    double value = distribution(generator);

    std::cout << value << std::endl;
    
}

