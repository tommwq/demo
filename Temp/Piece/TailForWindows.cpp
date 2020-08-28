/*
  stail
  Simple Tail。模仿Linux的Tail程序。
 */

#include <Windows.h>
#include <algorithm>

/*
DWORD WINAPI GetFileSize(
_In_       HANDLE hFile,
_Out_opt_  LPDWORD lpFileSizeHigh
);

DWORD WINAPI SetFilePointer(
_In_         HANDLE hFile,
_In_         LONG lDistanceToMove,
_Inout_opt_  PLONG lpDistanceToMoveHigh,
_In_         DWORD dwMoveMethod
);

BOOL WINAPI WriteConsole(
_In_        HANDLE hConsoleOutput,
_In_        const VOID *lpBuffer,
_In_        DWORD nNumberOfCharsToWrite,
_Out_       LPDWORD lpNumberOfCharsWritten,
_Reserved_  LPVOID lpReserved
);

HANDLE WINAPI GetStdHandle(
_In_  DWORD nStdHandle
);
*/

int main(int argc, _TCHAR* argv[])
{
    if (argc < 3){
        printf("stail filename line\n");
        return 0;
    }

    _TCHAR *filename = argv[1];
    size_t line = std::atoi((char *) argv[2]);
    HANDLE file;
    file = CreateFile(filename, 
        GENERIC_READ, 
        FILE_SHARE_READ | FILE_SHARE_WRITE, 
        NULL, 
        OPEN_EXISTING, 
        FILE_ATTRIBUTE_NORMAL, 
        NULL);

    if (file == INVALID_HANDLE_VALUE){
        printf("error: %d\n", GetLastError());
        return 0;
    }

    LARGE_INTEGER x;
    BOOL ret = GetFileSizeEx(file, &x);

    long segment = 0;
    long offset = 0;
    const int BUF_SIZE = 4096;
    char *buf = new char[BUF_SIZE];

    int count = 0;
    while (count < line){
        segment--;
        DWORD y = SetFilePointer(file, segment * BUF_SIZE, NULL, FILE_END);
        DWORD num;
        ReadFile(file, buf, BUF_SIZE, &num, NULL);
        count += std::count(buf, buf + BUF_SIZE, '\n');
    }
    for (int i = 0; i < BUF_SIZE; ++i){
        if (buf[i] == '\n'){
            --count;
        }
        if (count == line){
            break;
        }
        ++offset;
    }

    offset += (segment * BUF_SIZE);

    SetFilePointer(file, offset, NULL, FILE_END);

    DWORD num;
    while (true){
        ReadFile(file, buf, BUF_SIZE, &num, NULL);
        DWORD n;
        buf[num] = '\0';
        //WriteConsole(GetStdHandle(STD_OUTPUT_HANDLE), buf, num, &n, NULL);
        printf("%s\n", buf);
        if (num < BUF_SIZE){
            break;
        }
    }

    return 0;
}

