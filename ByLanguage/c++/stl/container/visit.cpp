#include <iostream>
#include <chrono>
#include <list>
#include <vector>
#include <numeric>

using namespace std;
using namespace std::chrono;

int main() {

    time_point<steady_clock> start, stop;
    microseconds duration;

    vector<int> v;
    list<int> l;

    for (int i = 0; i < 100 * 10000; i++) {
        v.push_back(i);
        l.push_back(i);
    }

    start = steady_clock::now();
    accumulate(v.begin(), v.end(), 0);
    stop = steady_clock::now();
    duration = duration_cast<microseconds>(stop - start);
    cout << "vector visit: " << duration.count() << " us" << endl;

    start = steady_clock::now();
    accumulate(l.begin(), l.end(), 0);
    stop = steady_clock::now();
    duration = duration_cast<microseconds>(stop - start);
    cout << "list visit: " << duration.count() << " us" << endl;
}
