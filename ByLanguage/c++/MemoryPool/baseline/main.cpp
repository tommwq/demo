// cl main.cpp /std:c++17 /EHsc

#include <iostream>
#include <chrono>

using namespace std;
using namespace std::chrono;

class Rational {
public:
    Rational(int a = 0, int b = 0)
        :n(a), d(b) {
    }
private:
    int n;
    int d;
};


int main() {
    Rational *array[1000];

    time_point<steady_clock> start_time;
    time_point<steady_clock> stop_time;
    microseconds duration;

    start_time = steady_clock::now();
    for (int j = 0; j < 500; j++) {
        for (int i = 0; i < 1000; i++) {
            array[i] = new Rational(i);
        }
        for (int i = 0; i < 1000; i++) {
            delete array[i];
        }
    }
    stop_time = steady_clock::now();

    duration = duration_cast<microseconds>(stop_time - start_time);

    cout << duration.count() << " us " << endl;
}
