/**
 * udp_receiver.cpp
 * 接受文本格式的udp消息，并打印出来。
 */

#include <cerrno>
#include <cstring>
#include <cstdlib>
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
  if (argc == 1){
    std::cout << "usage: " << argv[0] << " <port>" << std::endl;
    std::exit(0);
  }
  unsigned short port;
  port = std::atoi(argv[1]);
  int sock = socket(AF_INET, SOCK_DGRAM, IPPROTO_IP);
  if (sock == -1){
    std::cout << "Cannot create socket. " << strerror(errno) << std::endl;
    std::terminate();
  }
  struct sockaddr_in address;
  address.sin_family = AF_INET;
  address.sin_port = htons(port);
  address.sin_addr.s_addr = INADDR_ANY;
  int result = bind(sock, (struct sockaddr *) &address, sizeof(address));
  if (result == -1){
    std::cout << "Cannot create socket. " << strerror(errno) << std::endl;
    std::terminate();
  }
  std::string buffer(1024, '\0');
  ssize_t length = 0;
  time_t time_value;
  struct tm time_data;

  std::string log;
  while (true){
    struct sockaddr_in remote_address;
    socklen_t size = sizeof(remote_address);
    length = recvfrom(sock, (char *) buffer.c_str(), buffer.length(), 0, 
                      (struct sockaddr *) &remote_address, &size);
    if (length == -1){
      continue;
    }
    std::string host = inet_ntoa(remote_address.sin_addr);
    unsigned short port = ntohs(remote_address.sin_port);
    std::string time_buffer(64, '\0');
    if ((time_value = std::time(NULL)) != -1
        && localtime_r(&time_value, &time_data) != NULL){
      std::strftime((char *) time_buffer.c_str(), time_buffer.length(),
                    "%Y-%m-%d %H:%M:%S", &time_data);
    }
    if (time_buffer.empty()){
      time_buffer = "INVALID TIME";
    }
    log = buffer.substr(0, length);
    std::cout << host << ":" << port << " " << time_buffer.c_str()
              << "> " << log << std::endl;
  }
  close(sock);
  return 0;
}
