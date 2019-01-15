#include <iostream>
#include <chrono>
#include <list>
#include <vector>

using namespace std;
using namespace std::chrono;

int main() {

    time_point<steady_clock> start, stop;
    microseconds duration;

    vector<int> v;
    list<int> l;


    start = steady_clock::now();
    for (int i = 0; i < 100 * 10000; i++) {
        v.push_back(i);
    }
    stop = steady_clock::now();
    duration = duration_cast<microseconds>(stop - start);    
    cout << "vector push_back: " << duration.count() << " us" << endl;

    start = steady_clock::now();
    for (int i = 0; i < 100 * 10000; i++) {
        l.push_back(i);
    }
    stop = steady_clock::now();
    duration = duration_cast<microseconds>(stop - start);
    cout << "list push_back: " << duration.count() << " us" << endl;

    l.clear();
    start = steady_clock::now();
    for (int i = 0; i < 100 * 10000; i++) {
        l.push_front(i);
    }
    stop = steady_clock::now();
    duration = duration_cast<microseconds>(stop - start);
    cout << "list push_front: " << duration.count() << " us" << endl;
}
