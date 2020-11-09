#! /usr/bin/env python

#
# 2.7.2
#

import sys
import socket
import exceptions

def doProxy(listenPort, remoteIp, remotePort):
    print listenPort, remoteIp, remotePort
    try:
        # connect remote peer
        connectionSocket = socket.create_connection((remoteIp, remotePort))
        # listen 
        listenSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM, socket.IPPROTO_TCP)
        listenSocket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
        listenSocket.bind(('', int(listenPort)))
        listenSocket.listen(10)
        while True:
            (clientSocket, clientAddress) = listenSocket.accept()
            while True:
                buffer = clientSocket.recv(4096)
                if not buffer:
                    break
                print buffer
                connectionSocket.send(buffer)
                buffer = connectionSocket.recv(4096)
                clientSocket.send(buffer)
    except socket.error as e:
        print e
    except exceptions.AttributeError as e:
        print e
    except:
        print 'error', sys.exc_info()[0]
    return

if __name__ == '__main__':
    # get arguments
    if len(sys.argv) < 4:
        print 'missing arguments'
        print 'usage:', sys.argv[0], 'listenPort remoteIp remotePort'
        sys.exit()
    listenPort = sys.argv[1]
    remoteIp = sys.argv[2]
    remotePort = sys.argv[3]

    doProxy(listenPort, remoteIp, remotePort)
    print "done"
