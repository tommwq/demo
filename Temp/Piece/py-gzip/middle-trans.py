#!/usr/bin/env python
# -*- coding: utf-8 -*-

'''
简单的tcp转发服务器。
可以支持Mysql转发。
'''

import socket
import errno

def print_hex(string):
    line_width = 8
    remain = len(string)
    while 0 < remain:
        num = min(line_width, remain)
        for i in range(0, num):
            c = string[len(string) - remain + i]
            print '%2x' % ord(c),
            for i in range(num, line_width):
                print '  ',
            print '  ',
            for i in range(0, num):
                c = string[len(string) - remain + i]
                print '%c' % c,
        print ' '
        remain = remain - num


listenAddress = ('0.0.0.0', 10086)
forwardAddress = ('127.0.0.1', 9418)

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
        print 'recv from client'
        print_hex(buffer)
        send(remoteSocket, buffer)
        print 'send to remote'
        print_hex(buffer)
      buffer = recv(remoteSocket)
      if isinstance(buffer, str) and buffer != '':
        print 'recv from remot'
        print_hex(buffer)
        send(clientSocket, buffer)
        print 'send to client'
        print_hex(buffer)
    except socket.error, e:
      print e
      break
  remoteSocket.close()
  clientSocket.close()
  print clientAddress, ' closed'
  print forwardAddress, 'closed'
