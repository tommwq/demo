#include <stdio.h>
#include <windows.h>

#define VAR_WATCH() printf("nDividend=%d, nDivisor=%d, nResult=%d.\n", nDividend, nDivisor, nResult)



int main(int argc, char* argv[]) {
    int nDividend = 22, nDivisor = 0, nResult = 100;

    __try {
        printf("Before div in __try block:");
        VAR_WATCH();

        nResult = nDividend / nDivisor;
        
        printf("After div in __try block: ");
        VAR_WATCH();
        
    } __except(nDivisor = 1, VAR_WATCH(), printf("in except\n"), EXCEPTION_CONTINUE_EXECUTION) {
        printf("in handler\n");
        exit(0);
    }

    printf("end\n");
    return 0;
}
