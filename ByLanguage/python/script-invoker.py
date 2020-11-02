#!/usr/bin/env python
# -*- coding: utf-8 -*-

# a simple rpc shell

import socket
import subprocess
import logging
import time

running = True
logname = "a.log"

def handle(client):
    try:
        buffer = client.recv(1024)
        client.close()
    except:
        return
    
    params = buffer.split(' ')
    print params
    if params[0] == 'stop':
        global running
        running = False
        return
    params = ["/bin/bash"].extend(params)
    start = str(int(time.time()))
    try:
        proc = subprocess.Popen(params)
        proc.wait()
        print proc.stdout
        print proc.stderr
        print proc.returncode
    except Exception, e:
        print e
    global logname
    stop = str(int(time.time()))
    log = open(logname, "a")
    log.write("call " + buffer + " returns " + str(proc.returncode))
    log.write(" start: " + start + " stop: " + stop + "\n")
    log.close()


listener = socket.socket(socket.AF_INET, socket.SOCK_STREAM, 0);
listener.bind(("localhost", 62000));
listener.listen(100);

while running:
    try:
        client, address = listener.accept()
    except:
        pass
    handle(client)



    
