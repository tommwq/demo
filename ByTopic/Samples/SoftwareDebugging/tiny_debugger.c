#include <Windows.h>

void Help() {
    printf("TinyDbgr <PID of Program to Debug|\n   "
           "<Full Exe File Name> [Program Parameters]\n");
}

int main(int argc, char *argv[]) {
    if (argc <= 1) {
        Help();
        return -1;
    }

    if (strstr(strupr(argv[1]), ".EXE")) {
        TCHAR szCmdLine[MAX_PATH];
        szCmdLine[0] = '\0';
            
        for (int i = 1; i < argc; i++) {
            strcat(szCmdLine, argv[i]);
            if (i < argc) {
                strcat(szCmdLine, " ");
            }
        }

        if (!DbgNewProcess(szCmdLine)) {
            return -2;
        }
    } else if (!DebugActiveProcess(atoi(argv[1]))) {
        printf("Failed in DebugActiveProcess() with %d .\n", GetLastError());
        return -2;
    }

    return DbgMainLoop();
}
