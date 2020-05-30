// fault.cpp
// 展示错误类异常的程序，出自《软件调试》。

#include <stdio.h>
#include <windows.h>

#define VAR_WATCH() printf("nDividend=%d, nDivisor=%d, nResult=%d.\n", nDividend, nDivisor, nResult)

int main(int argc, char *argv[]) {
    int nDividend = 22;
    int nDivisor = 0;
    int nResult = 100;

    __try {
        printf("Before div in __try block: ");
        VAR_WATCH();

        nResult = nDividend / nDivisor;

        printf("After div in __try block: ");
        VAR_WATCH();
    } __except(
        printf("In __except block: "),
        VAR_WATCH(),
        GetExceptionCode() == EXCEPTION_INT_DIVIDE_BY_ZERO ? (nDivisor = 1,
                                                              printf("Divide Zero exception detected: "),
                                                              VAR_WATCH(),
                                                              EXCEPTION_CONTINUE_EXECUTION):
        EXCEPTION_CONTINUE_SEARCH) {
          
        printf("In handler block.\n");
    }

    return getchar();
}
