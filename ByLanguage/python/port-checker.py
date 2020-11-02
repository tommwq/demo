#! /usr/bin/python

''' 
  ¶Ë¿ÚÉ¨ÃèÆ÷
  port-scanner
  for python 2.x
  see usage()

  port-scanner host port
  port-scanner host_file
'''

'''
todo:
  1. use multi-thread to scan port 
'''

import sys
import socket
import time

# port stats: OPEN CLOSED NOT_SCANNED SCANNING


port_list = {}

def check_port(host, port, timeout = 3):
    # todo: check if host is a string.
    success = False
    try:
        conn = socket.create_connection((host, port), timeout)
        success = True
    except Exception, e:
#        print e
        pass
    if success:
        state = 'OPEN'
    else:
        state = 'CLOSED'
    return state

def scan_one(host, port):
    state = check_port(host, port)
    print host + ':' + port + '\t\t' + state

def get_port(filename):
    '''
    get (host, port) tuple from a text file. the file shuold like fellow:
      www.baidu.com:80 # this is a comment
      192.168.10.11:11211
    '''
    file = open(filename, 'r')
    for line in file:
        # get rid of comment
        line = line[:line.find('#')]
        # pass null line
        if len(line) == 0:
            continue
        line = line.strip()
        sep = line.find(':')
        if sep == -1:
            print 'error: do you forget the ":" between host and port? >> ' + line
            continue
        host = line[:sep]
        port =  line[sep + 1:]
        port_list[(host, port)] = 'NOT_SCANNED'
    return

def usage():
    usage_message = sys.argv[1] + ' : a little port scanner\n' + \
        'usage:\n' + \
        '\t' + sys.argv[1] + ' host port\n' + \
        '\t' + sys.argv[1] + ' host_file\n';
    print usage_message

def scan_more(filename):
    get_port(filename)
    max_thread_num = 10
    for item in port_list.keys():
        host, port=  item
        port_list[item] = check_port(host, port, 1)
 
    for item in port_list.items():
        (host, port), state = item
        print host + ':' + port + '\t\t' + state
    return 'OK'

def main():
    if len(sys.argv) == 1:
        usage()
    
    print 'Start scanning:'
    if len(sys.argv) == 2:
        scan_more(sys.argv[1])
    else:
        scan_one(sys.argv[1], sys.argv[2])
    print 'Done'
    return

if __name__ == '__main__':
    main()

