#pragma once

#include <iostream>
#include <string>

using namespace std;

class Trace2 {
public:
    Trace2(const char *name);
    ~Trace2();
    
    static bool traceIsActive;
private:
    string theFunctionName;
};

inline Trace2::Trace2(const char *name)
    :theFunctionName(name) {
    if (traceIsActive) {
        cerr << "Enter function " << name << endl;
    }
}

inline Trace2::~Trace2() {
    if (traceIsActive) {
        cerr << "Exit function " << theFunctionName << endl;
    }
}

