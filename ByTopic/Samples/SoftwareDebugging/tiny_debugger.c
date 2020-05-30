#include <stdio.h>
#include <Windows.h>

BOOL DbgMainLoop(DWORD dwWaitMS) {
    DEBUG_EVENT DbgEvt;
    DWORD dwContinueStatus = DBG_CONTINUE;
    BOOL bExit = FALSE;

    while (!bExit) {
        if (!WaitForDebugEvent(&DbgEvt, dwWaitMS)) {
            printf("WaitForDebugEvent() returned False %d.\n", GetLastError());
            bExit = TRUE;
            continue;
        }

        printf("Debug event received from process %d thread %d: %s.\n",
               DbgEvt.dwProcessId,
               DbgEvt.dwThreadId,
               DbgEventName[DbgEvt.dwDebugEventCode > MAX_DBG_EVENT ? MAX_DBG_EVENT : DbgEvt.dwDebugEventCode-1]);

        switch (DbgEvt.dwDebugEventCode) {
        case EXCEPTION_DEBUG_EVENT:
            printf("-Debugee breaks into debugger; press any key to continue.\n");
            getchar();
            switch (DbgEvt.u.Exception.ExceptionRecord.ExceptionCode) {
            case EXCEPTION_ACCESS_VIOLATION:
                break;
            case EXCEPTION_BREAKPOINT:
                break;
            case EXCEPTION_DATATYPE_MISALIGNMENT:
                break;
            case EXCEPTION_SINGLE_STEP:
                break;
            case DBG_CONTROL_C:
                break;
            default:
                break;
            }

        case CREATE_THREAD_DEBUG_EVENT:
            break;
        case CREATE_PROCESS_DEBUG_EVENT:
            break;
        case EXIT_THREAD_DEBUG_EVENT:
            break;
        case EXIT_PROCESS_DEBUG_EVENT:
            break;
        case LOAD_DLL_DEBUG_EVENT:
            break;
        case UNLOAD_DLL_DEBUG_EVENT:
            break;
        case OUTPUT_DEBUG_STRING_EVENT:
            break;
        }

        ContinueDebugEvent(DbgEvt.dwProcessId,
                           DbgEvt.dwThreadId,
                           dwContinueStatus);
    }

    return TRUE;
}

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
