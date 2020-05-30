import argparse
import logging
import signal
import socket


from util import *
from event import *
from echoserver import EchoServer

'''
echo_server --port=1000 --log-level=debug
'''

# global objects
echo_server = EchoServer()

def signal_handler(signal_number, frame):
    logging.debug('程序收到信号 %d.', signal_number)
    echo_server.shutdown()
    logging.debug('已关闭服务.')
            
if __name__ == '__main__':
    argument_parser = argparse.ArgumentParser(description='echo server')
    argument_parser.add_argument('-port',
                                 nargs=1,
                                 default=10000,
                                 type=int)
    
    argument_parser.add_argument('-log-level',
                                 nargs=1,
                                 type=str,
                                 default='debug',
                                 choices=['verbose','debug','info','warning','error','fatal'])
    
    arguments = argument_parser.parse_args()

    log_level_table = {
        'verbose': logging.NOTSET,
        'debug': logging.DEBUG,
        'info': logging.INFO,
        'warning': logging.WARNING,
        'error': logging.ERROR,
        'fatal': logging.CRITICAL,
    }
    
    logging.basicConfig(level=log_level_table[arguments.log_level],
                        format='%(asctime)s [%(levelname)s] %(message)s')

    signal.signal(signal.SIGINT, signal_handler)
    signal.signal(signal.SIGTERM, signal_handler)

    logging.debug('已启动程序.')
    
    echo_server.set_port(arguments.port)
    echo_server.start()
