#include <stdio.h>

#if defined(__unix__)
#define OS_LINUX
#endif

#if defined(_WIN32)
#define OS_WINDOWS
#endif

#if defined(OS_LINUX)
#include <unistd.h>
#endif

#if defined(OS_WINDOWS)
#include <Windows.h>
#endif


int main(int argc, char* argv[]) {
    long page_size;
#if defined(OS_LINUX)
    page_size = sysconf(_SC_PAGESIZE); // 或getpagesize()
#elif defined(OS_WINDOWS)
    SYSTEM_INFO system_info;
    GetSystemInfo(&system_info);
    page_size = system_info.dwPageSize;
#else
    fprintf(stderr, "unsupported operation system.\n");
    return -1;
#endif
    
    fprintf(stdout, "%ld\n", page_size);
}
