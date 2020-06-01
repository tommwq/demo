/*
  利用异常结构绕过溢出保护攻击的有问题的例子程序except.c。
  vc6.0下编译。
  yuange@nsfocus.com
*/

#pragma comment(lib, "ws2_32.lib")

#include <windows.h>
#include <winsock.h>
#include <stdio.h>

int main(int argc, char **argv) {
    int  j;
    char *str;
    char buff1[0x0f80];
    char buff2[0x1000];
    struct sockaddr_in s_in;
    struct sockaddr addr;
    SOCKET fd ,fd1;
    u_short port;
    int result, i, recvbytes;
    WSADATA wsaData;

    result = WSAStartup(MAKEWORD(1, 1), &wsaData);
    if (result != 0) {
        printf("\n SOCKET err!\n ");
        exit(1);
    }
    
    j=0;
    
    str = argv[0];
    if(argc>1) port = atoi(argv[1]);
    else port = 1080;
    fd  =  socket(AF_INET, SOCK_STREAM,0);
    s_in.sin_family = AF_INET;
    s_in.sin_port = htons(port);
    s_in.sin_addr.s_addr = 0;

    bind(fd, &s_in, sizeof(s_in));
    listen(fd, 10);

    i = sizeof(addr);
    fd1 = accept(fd, &addr, &i);

    recvbytes = recv(fd1, buff2, 0x1000, 0);

    if (recvbytes > 0){
        buff2[recvbytes] = 0;
        buff2[0x1000-1] = 0;
        printf("\n recv 0x%x bytes \n", recvbytes);
        strcpy(buff1, buff2);
        printf("\n the program %s recv :\n %s \n ", argv[0], buff2);
    }

    closesocket(fd1);
    closesocket(fd);
    WSACleanup();

    /*
      溢出后会覆盖j，被检测到，这就相当于一些溢出保护
    */
    if (j != 0) {
        printf("\n the program %s buffover err !",argv[0]);
/*
  这儿溢出后可能因为argv[0]被覆盖，而发生异常，
  具体环境这代码可能在前面。
  这就相当于形参被破坏。
*/
        exit(1);
    }

}
