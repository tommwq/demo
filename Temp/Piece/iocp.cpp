
#undef NDEBUG
#include <cassert>
#include <cstdio>
#include <map>
#include <ctime>
#include <winsock2.h>
#include <windows.h>
#include <process.h>

#pragma comment(lib, "ws2_32.lib")

/*
  0x6 ERROR_INVALID_HANDLE
  996 WSA_IO_PENDING  Overlapped operations will complete later.
  64 ERROR_NETNAME_DELETED
64 (0x40)
The specified network name is no longer available.
*/

// todo: 用4k的缓冲区，发送一个1Mb的数据，观察程序运行情况。

struct cpkey_st {
  WSABUF *buf;
  int sock;
};

#define BUFFER_SIZE 64

void onMessage(struct cpkey_st *key, int len);
void onConnect(struct cpkey_st *key);
void onClose(struct cpkey_st *key);
void worker(void *cp);
void checker(void *null);

std::map<int, int> sockets;
std::map<int, clock_t> timers;

HANDLE cp;

/*
  arguments:
  listen-port, forward-port, forward-host, timeout
*/
int main(void){
  int ret;
  WSADATA wsaData;
  WORD wsaVersion;

  // 初始化socket库
  wsaVersion = MAKEWORD(2, 0);
  ret = WSAStartup(wsaVersion, &wsaData);
  assert(ret != -1);

  // 创建完成端口对象
  cp = CreateIoCompletionPort(INVALID_HANDLE_VALUE, NULL, 0, 0);
  assert(cp != INVALID_HANDLE_VALUE);  

  // 启动工作线程
  _beginthread(worker, 0, (void *)cp);
  _beginthread(checker, 0, NULL);

  // 创建监听套接字
  int listenSocket = WSASocket(AF_INET, SOCK_STREAM, IPPROTO_TCP, 
                               NULL, 0, WSA_FLAG_OVERLAPPED);

  struct sockaddr_in address;
  address.sin_family = AF_INET;
  address.sin_port = htons(10086);
  address.sin_addr.s_addr = INADDR_ANY;
  ret = bind(listenSocket, (struct sockaddr*)&address, sizeof(address));
  assert(ret != -1);

  ret = listen(listenSocket, 1000);
  assert(ret != -1);

  DWORD flags = 0;

  // 处理连接请求
  while (1){
    int clientSocket = WSAAccept(listenSocket, NULL, NULL, NULL, 0);
    assert(clientSocket != -1);

    // 创建保存数据的对象
    WSABUF *wsaBuf = new WSABUF;
    wsaBuf->buf = new char[BUFFER_SIZE];
    wsaBuf->len = BUFFER_SIZE;
    struct cpkey_st *key = new struct cpkey_st;
    key->sock = clientSocket;
    key->buf = wsaBuf;

    OVERLAPPED *ol = new OVERLAPPED;
    ZeroMemory(ol, sizeof(*ol));
    ol->hEvent = WSACreateEvent();
    assert(ol->hEvent != NULL);

    // 将套接字绑定到iocp对象
    ret = (int) CreateIoCompletionPort((HANDLE)clientSocket, cp,  (ULONG_PTR)key, 3);
    assert((HANDLE)ret == cp);

    ret = WSARecv(clientSocket, wsaBuf, 1, NULL, &flags, ol, NULL);
    onConnect(key);
  }

  WSACleanup();
  return 0;
}

void worker(void *_cp){
  HANDLE cp = (HANDLE)_cp;
  int ret;
  DWORD len;
  OVERLAPPED *ol;
  DWORD flags = 0;
  struct cpkey_st *key;
  
  // ? iocp how to determin peer closed connection ?

  while (1){
    ret = GetQueuedCompletionStatus(cp, &len, (LPDWORD)&key, &ol, INFINITE);
    if (ret != TRUE){
      perror("");
      printf("error: %d\n", GetLastError());
      continue;
    }
    //    assert(ret != FALSE);

    if (len > 0){
      onMessage(key, len);
    } else {
      onClose(key);
      CloseHandle((HANDLE)key->sock);
    }
    ret = WSARecv(key->sock, key->buf, 1, NULL, &flags, ol, NULL);
  }
}

void onMessage(struct cpkey_st *key, int len){
  int sock = key->sock;
  DWORD num;
  WSABUF buf;
  buf.buf = key->buf->buf;
  buf.len = len;
  WSASend(sockets[sock], &buf, 1, &num, 0, NULL, NULL);

  if (timers[sock] == 0){
    timers[sockets[sock]] = clock();
  } else {
    timers[sock] = clock();
  }
}

void onClose(struct cpkey_st *key){
  printf("socket %d disconnected.\n", key->sock);
  int sock = key->sock;
  int servSock = sockets[sock];
  CloseHandle((HANDLE)sock);
  CloseHandle((HANDLE)servSock);
  timers[sock] = (clock_t)0;
}

void onConnect(struct cpkey_st *key){
  printf("socket %d connected.\n", key->sock);

  int servSock = WSASocket(AF_INET, SOCK_STREAM, IPPROTO_TCP, NULL, 0, WSA_FLAG_OVERLAPPED);
  struct sockaddr_in address;
  address.sin_family = AF_INET;
  address.sin_port = htons(80);
  address.sin_addr.s_addr = inet_addr("127.0.0.1");
  int ret = WSAConnect(servSock, (struct sockaddr*)&address, sizeof(address), NULL, NULL, NULL, NULL);
  assert(ret != SOCKET_ERROR);

  int sock = key->sock;
  sockets[sock] = servSock;
  sockets[servSock] = sock;
  timers[sock] = clock();

  WSABUF *wsaBuf = new WSABUF;
  wsaBuf->buf = new char[BUFFER_SIZE];
  wsaBuf->len = BUFFER_SIZE;

  struct cpkey_st *skey = new struct cpkey_st;
  skey->sock = servSock;
  skey->buf = wsaBuf;

  OVERLAPPED *ol = new OVERLAPPED;
  ZeroMemory(ol, sizeof(*ol));
  ol->hEvent = WSACreateEvent();
  assert(ol->hEvent != NULL);

  DWORD flags = 0;

  // 将套接字绑定到iocp对象
  ret = (int) CreateIoCompletionPort((HANDLE)servSock, cp,  (ULONG_PTR)skey, 3);
  assert((HANDLE)ret == cp);

  ret = WSARecv(servSock, wsaBuf, 1, NULL, &flags, ol, NULL);
}


void checker(void *null){
  static int limit = 6 * CLOCKS_PER_SEC;
  static clock_t old;

  while (1){
    clock_t now = clock();
    old = now - limit;
    // todo lock
    std::map<int, clock_t>::iterator it;
    for (it = timers.begin(); it != timers.end(); ++it){
      if (it->second != 0 && now - it->second > limit){
        int sock = it->first;
        //        continue;
        CloseHandle((HANDLE)sock);
        CloseHandle((HANDLE)(sockets[sock]));
        printf("timeout %d %d(%d, %d, %d)\n", sock, sockets[sock], it->second, now, now - it->second - limit);
        it->second = 0;
      } else if (old < it->second){
        old = it->second;
      }
    }
    printf("sleep: %d\n", now - old);
    Sleep(now - old);
  }
}
