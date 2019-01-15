#include <chrono>
#include <iostream>
#include <iomanip>
#include <ratio>

#include "AddOne.h"
#include "Trace.h"
#include "Trace2.h"
#include "Trace3.h"

using namespace std::chrono;

void print_result(const string &tag, const microseconds &duration) {
    cout << std::setw(12) << std::left << tag
         << std::setw(10) << std::right << duration.count() << " ms"
         << std::endl;
}

int main() {
    int round = 10 * 10000;
    int x;
    time_point<steady_clock> start;
    time_point<steady_clock> stop;
    microseconds duration;
       


    start = steady_clock::now();
    for (int i = 0; i < round; i++) {
        x = baseline(i);
    }
    stop = steady_clock::now();

    duration = duration_cast<microseconds>(stop - start);
    print_result("baseline", duration);


    Trace::traceIsActive = false;
    start = steady_clock::now();
    for (int i = 0; i < round; i++) {
        x = add_one(i);
    }
    stop = steady_clock::now();

    duration = duration_cast<microseconds>(stop - start);
    print_result("trace1 off", duration);

    Trace::traceIsActive = true;
    start = steady_clock::now();
    for (int i = 0; i < round; i++) {
        x = add_one(i);
    }
    stop = steady_clock::now();

    duration = duration_cast<microseconds>(stop - start);
    print_result("trace1  on", duration);



    Trace2::traceIsActive = false;
    start = steady_clock::now();
    for (int i = 0; i < round; i++) {
        x = add_one2(i);
    }
    stop = steady_clock::now();

    duration = duration_cast<microseconds>(stop - start);
    print_result("trace2 off", duration);

    Trace2::traceIsActive = true;
    start = steady_clock::now();
    for (int i = 0; i < round; i++) {
        x = add_one2(i);
    }
    stop = steady_clock::now();

    duration = duration_cast<microseconds>(stop - start);
    print_result("trace2  on", duration);
    

    Trace3::traceIsActive = false;
    start = steady_clock::now();
    for (int i = 0; i < round; i++) {
        x = add_one3(i);
    }
    stop = steady_clock::now();

    duration = duration_cast<microseconds>(stop - start);
    print_result("trace3 off", duration);

    Trace3::traceIsActive = true;
    start = steady_clock::now();
    for (int i = 0; i < round; i++) {
        x = add_one3(i);
    }
    stop = steady_clock::now();

    duration = duration_cast<microseconds>(stop - start);
    print_result("trace3  on", duration);

}
