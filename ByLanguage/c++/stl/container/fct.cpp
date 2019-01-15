#include <iostream>
#include <chrono>
#include <list>
#include <vector>
#include <numeric>

using namespace std;
using namespace std::chrono;

int mult(int x, int y) {
    return x * y;
}

class Mult {
public:
    int operator()(int x, int y) const {
        return x + y;
    }
};

int main() {

    time_point<steady_clock> start, stop;
    microseconds duration;

    vector<int> v;

    for (int i = 0; i < 1000 * 10000; i++) {
        v.push_back(i);
    }

    start = steady_clock::now();
    accumulate(v.begin(), v.end(), 0, mult);
    stop = steady_clock::now();
    duration = duration_cast<microseconds>(stop - start);
    cout << "function: " << duration.count() << " us" << endl;

    start = steady_clock::now();
    accumulate(v.begin(), v.end(), 0, Mult());
    stop = steady_clock::now();
    duration = duration_cast<microseconds>(stop - start);
    cout << "function object: " << duration.count() << " us" << endl;
}
