#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <winsock2.h>
#include <windows.h>

#pragma comment(lib, "ws2_32.lib")


#define error()  { perror(__FUNCTION__); exit(1); }

int main(void){

  WORD          wsa_version;
  WSADATA       wsa_data;
  int           listen_socket;
  struct sockaddr_in  listen_address;
  int           length;
  int           client_socket;
  struct sockaddr_in  client_address;
  char          *buffer;
  int           offset;

  wsa_version = MAKEWORD(2, 0);
  if (WSAStartup(wsa_version, &wsa_data) == -1){
    error();
  }

#define PORT 10000

  if ((listen_socket = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP)) == -1){
    error();
  }

  listen_address.sin_family = AF_INET;
  listen_address.sin_port = htons(PORT);
  listen_address.sin_addr.s_addr = INADDR_ANY;
  length = sizeof(listen_address);
  if (bind(listen_socket, (struct sockaddr *) &listen_address, length) == -1){
    error();
  }

  if (listen(listen_socket, 10) == -1){
    error();
  }
  
  length = sizeof(client_address);
  if ((client_socket = accept(listen_socket, (struct sockaddr *) &client_address, &length)) == -1){ 
    error();
  }

#define BUFFER_SIZE 1024 * 1024

  if ((buffer = (char *) malloc(BUFFER_SIZE)) == NULL){
    error();
  }

  while ((length = recv(client_socket, buffer, BUFFER_SIZE, 0)) > 0){
    offset = 0;
    while (length > 0){
      int num, i;
      num = length > 8 ? 8 : length;
      printf("\t");
      for (i = 0; i < num; ++i){
        printf("%3x", buffer[offset + i]);
      }
      for (i = num; i < 8; ++i){
        printf("   ");
      }
      printf("\t");
      for (i = 0; i < num; ++i){
        if (isprint(buffer[offset + i])){
          printf("%c", buffer[offset + i]);
        } else {
          printf(".");
        }
      }
      printf("\n");

      offset += num;
      length -= num;
    }
  }

  WSACleanup();

  return 0;
}