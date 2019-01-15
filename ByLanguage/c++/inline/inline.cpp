#include <iostream>
#include <chrono>

using namespace std;
using namespace std::chrono;

__declspec(noinline) int add(int a, int b) {
    return a+b;
}

__forceinline int add2(int a, int b) {
    return a+b;
}

int main() {
    int x[1000];
    int y[1000];
    int z[1000];

    time_point<steady_clock> start, stop;
    microseconds duration;

    start = steady_clock::now();
    for (int i = 0; i < 1000; ++i) {
        for (int j = 0; j < 1000; ++j) {
            for (int k = 0; k < 1000; ++k) {
                z[i] = add(y[j], x[k]);
            }
        }
    }
    stop = steady_clock::now();
    duration = duration_cast<microseconds>(stop - start);
    cout << "add:  " << duration.count() << " us" << endl;

    start = steady_clock::now();
    for (int i = 0; i < 1000; ++i) {
        for (int j = 0; j < 1000; ++j) {
            for (int k = 0; k < 1000; ++k) {
                z[i] = add2(y[j], x[k]);
            }
        }
    }
    stop = steady_clock::now();
    duration = duration_cast<microseconds>(stop - start);
    cout << "add2: " << duration.count() << " us" << endl;
}
