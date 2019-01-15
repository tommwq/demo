#pragma once

#include <iostream>
#include <string>

using namespace std;

class Trace {
public:
    Trace(const string &name);
    ~Trace();
    
    static bool traceIsActive;
private:
    string theFunctionName;
};

inline Trace::Trace(const string &name)
    :theFunctionName(name) {
    if (traceIsActive) {
        cerr << "Enter function " << name << endl;
    }
}

inline Trace::~Trace() {
    if (traceIsActive) {
        cerr << "Exit function " << theFunctionName << endl;
    }
}

