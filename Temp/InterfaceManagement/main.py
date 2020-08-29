#!/usr/bin/env python
#--coding:utf-8--

'''
python main.py
'''

import http.server
import signal
import sys

import config
import server
import repository

def sigint_handler(signum, frame):
    sys.exit(-1)
    
def run():
    server_address = ('', config.port)
    http_server = http.server.HTTPServer(server_address, server.HTTPHandler)
    http_server.serve_forever()

if __name__ == '__main__':
    signal.signal(signal.SIGINT, sigint_handler)
    run()
