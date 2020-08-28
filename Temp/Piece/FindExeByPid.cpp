/**
 * FindExeByPid.cpp
 * 按照pid查找程序位置。
 * 2014-07-01 tommwq
 */

#include <cstdio>
#include <cstdlib>
#include <iostream>
#define UNICDOE
#include <windows.h>

int wmain(int argc, wchar_t **argv){
  if (argc == 1){
    std::wcout << L"usage: " << argv[0] << L"<pid>" << std::endl;
    std::exit(0);
  }
  DWORD pid = _wtoi(argv[1]);
  HANDLE process = ::OpenProcess(PROCESS_QUERY_INFORMATION, false, pid);
  if (process == NULL){
    std::wcout << L"ERROR: " << ::GetLastError() << std::endl;
    std::exit(0);
  }
  DWORD length(MAX_PATH);
  wchar_t buffer[MAX_PATH];
  if (!::QueryFullProcessImageName(process, 0, buffer, &length)){
    std::wcout << L"ERROR: " << ::GetLastError() << std::endl;
    std::exit(0);
  }
  std::wcout << L"PID: " << pid << L" IMAGE: " << buffer << std::endl;
  return 0;
}
