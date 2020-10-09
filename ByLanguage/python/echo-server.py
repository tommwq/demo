#!/usr/bin/env python
# -*- coding: utf-8 -*-

'''
简单的回显服务器。
'''

import socket

listenAddress = ('0.0.0.0', 10086)

listenSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
listenSocket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
listenSocket.bind(listenAddress)
listenSocket.listen(10)

while True:
  clientSocket, clientAddress = listenSocket.accept()
  print 'on connect ', clientAddress
  quit = False
  while not quit:
    line = ''
    buffer = clientSocket.recv(4096)
    if len(buffer) == 0:
      continue
    while buffer[-1] != '\n':
      line += buffer
      buffer = clientSocket.recv(4096)
    print 'on recv ', line
    clientSocket.send(line)
    if line.strip(' \n\r\t') == 'quit':
      print 'peer send quit'
      quit = True
  clientSocket.close()
  print 'close'
