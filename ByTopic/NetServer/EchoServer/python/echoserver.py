import argparse
import logging
import signal
import socket

from util import *
from event import *

# TODO 改为事件驱动
# TODO 增加epoll
# TODO 进行压力测试
# TODO 处理signal

class EchoServer(object):

    @classmethod
    def default_port(cls):
        return 10000

    def __init__(self):
        self._port = self.default_port()
        self.listen_socket = None
        self.client_socket = None
        self._logger = logging.getLogger(name=None)

    def __del__(self):
        self.shutdown()

    def shutdown(self):
        self.close_listen_socket()
        self.close_client_socket()

    @noexcept
    def close_listen_socket(self):
        if self.listen_socket:
            self.listen_socket.close()

    @noexcept
    def close_client_socket(self):
        if self.client_socket:
            self.client_socket.close()

    def set_port(self, port):
        self._port = port;

    def set_logger(self, logger):
        self._logger = logger;

    def start(self):
        listen_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self._logger.debug('已建立套接字.')
        
        host = '0.0.0.0'
        bind_address = (host, self._port)
        listen_socket.bind(bind_address)
        self._logger.debug('已绑定地址 %s.', host)

        self._logger.debug('监听端口 %d.', self._port)
        listen_socket.listen()
        self.listen_socket = listen_socket
        
        self._logger.debug('将进入主循环.')
        while True:
            self._logger.debug('等待连接.')
            client_socket, client_address = self.listen_socket.accept()
            fileno = client_socket.fileno()
            self._logger.debug('已建立连接 %d. 地址 %s.', fileno, client_address)
            while True:
                data = client_socket.recv(1024)
                if not data:
                    self._logger.debug('对方要求断开连接 %d.', fileno)
                    break
                
                self._logger.debug('接收 %d 字节.', len(data))
                client_socket.sendall(data)
                self._logger.debug('发送 %d 字节.', len(data))

            client_socket.close()
            self._logger.debug('已断开连接 %d.', fileno)


