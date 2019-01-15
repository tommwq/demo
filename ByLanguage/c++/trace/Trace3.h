#pragma once

#include <iostream>
#include <string>

using namespace std;

class Trace3 {
public:
    Trace3(const char *name);
    ~Trace3();
    
    static bool traceIsActive;
private:
    string *theFunctionName;
};

inline Trace3::Trace3(const char *name)
    :theFunctionName(nullptr) {
    if (traceIsActive) {
        cerr << "Enter function " << name << endl;
        theFunctionName = new string(name);
    }
}

inline Trace3::~Trace3() {
    if (traceIsActive) {
        cerr << "Exit function " << *theFunctionName << endl;
        delete theFunctionName;
    }
}

