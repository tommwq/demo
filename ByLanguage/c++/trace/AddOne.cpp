#include "AddOne.h"

#include "Trace.h"
#include "Trace2.h"
#include "Trace3.h"

int baseline(int x) {
    return x + 1;
}

int add_one(int x) {
    Trace t(__FUNCTION__);
    return x + 1;
}

int add_one2(int x) {
    Trace2 t(__FUNCTION__);
    return x + 1;
}

int add_one3(int x) {
    Trace3 t(__FUNCTION__);
    return x + 1;
}
