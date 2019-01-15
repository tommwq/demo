#include <iostream>
#include <chrono>
#include <string>
#include <map>
#include <cmath>
#include <random>

using namespace std;
using namespace std::chrono;

int main() {
    time_point<steady_clock> start, stop;
    microseconds duration;

    map<string,map<double,int>> m;
    random_device random_device;
    uniform_real_distribution<double> distribution(10.0, 20.0);

    start = steady_clock::now();
    for (int i = 0; i < 4000; i++) {
        for (int j = 0; j < 50; j++) {
            m[to_string(i)][distribution(random_device)] = j;
        }
    }
    stop = steady_clock::now();
    cout << duration_cast<milliseconds>(stop-start).count() << " ms" << endl;

    const int count = 10000;
    map<string,double> input;
    while (input.size () < count) {
        uniform_int_distribution<int> stock(0, 3999);
        string stock_code = to_string(stock(random_device));
        double price = distribution(random_device);
        map[stock_code] = price;
    }

    map<string,int> batch;
    start = steady_clock::now();
    for (const auto &entry: input) {
        string s = entry.first;
        double price = entry.second;

        for (auto iterator = m[s].lower_bound(price);
             iterator != m[s].end();
             iterator++) {
            batch[s] = iterator->second;
        }
    }
    stop = steady_clock::now();
    cout << duration_cast<milliseconds>(stop-start).count() << " ms" << endl;
}
