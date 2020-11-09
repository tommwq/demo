/**
 * udp_sender.cpp
 * 发送文本格式的udp消息，并打印出来。
 */

#include <cerrno>
#include <cstdlib>
#include <cstring>
#include <ctime>
#include <iostream>
#include <string>
#include <vector>
#include <unistd.h>
#include <arpa/inet.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <netinet/in.h>

int main(int argc, char **argv){
  if (argc < 3){
    std::cout << "usage: " << argv[0] << " <host> <port>" << std::endl;
    std::exit(0);
  }
  std::string host(argv[1]);
  unsigned short port = std::atoi(argv[2]);
  int sock = socket(AF_INET, SOCK_DGRAM, IPPROTO_IP);
  if (sock == -1){
    std::cout << "Cannot create socket. " << strerror(errno) << std::endl;
    std::terminate();
  }
  struct sockaddr_in address;
  address.sin_family = AF_INET;
  address.sin_port = htons(port);
  address.sin_addr.s_addr = inet_addr(host.c_str());
  std::string buffer(1024, '\0');
  ssize_t length = 0;
  time_t time_value;
  struct tm time_data;
  std::string time_buffer(64, '\0');
  std::string log;
  while (true){
    std::cout << "log> ";
    std::fill(buffer.begin(), buffer.end(), '\0');
    std::cin.getline((char *) buffer.c_str(), buffer.length());
    length = sendto(sock, (char *) buffer.c_str(), std::strlen(buffer.c_str()), 
                    0, (struct sockaddr *) &address, sizeof(address));
    if (length == -1){
      continue;
    }
  }
  close(sock);
  return 0;
}
