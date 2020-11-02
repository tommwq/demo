#!/usr/bin/env python
# -*- coding: utf-8 -*-

'''
简单的tcp转发服务器。
可以支持Mysql转发。
'''

import socket
import errno


listenAddress = ('0.0.0.0', 9418)
forwardAddress = ('172.19.103.14', 9418)

def recv(sock, len = 4096):
  try:
    return sock.recv(len)
  except socket.error, e:
    if e.errno == errno.EAGAIN:
      return ''
    else:
      raise e

def send(sock, buffer):
  try:
    sock.send(buffer)
  except socket.error, e:
    if e.errno != errno.EAGAIN:
      raise e
  
listenSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
listenSocket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
listenSocket.setblocking(False)
listenSocket.bind(listenAddress)
listenSocket.listen(10)

while True:
  try:
    clientSocket, clientAddress = listenSocket.accept()
    print clientAddress, ' connected'
  except socket.error, e:
    if errno.EAGAIN == e.errno:
      continue
    else:
      print e.errno
      break
  clientSocket.setblocking(False)
  remoteSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
  try:
    remoteSocket.connect(forwardAddress)
    print forwardAddress, 'connected'
  except socket.error, e:
    print 'error: ', e
    continue
  remoteSocket.setblocking(False)
  while True:
    try:
      buffer = recv(clientSocket)
      if isinstance(buffer, str) and buffer != '':
        print 'recv from client', buffer
        send(remoteSocket, buffer)
        print 'send to remote', buffer
      buffer = recv(remoteSocket)
      if isinstance(buffer, str) and buffer != '':
        print 'recv from remot', buffer
        send(clientSocket, buffer)
        print 'send to client'
    except socket.error, e:
      print e
      break
  remoteSocket.close()
  clientSocket.close()
  print clientAddress, ' closed'
  print forwardAddress, 'closed'
