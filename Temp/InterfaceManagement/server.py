from http.server import BaseHTTPRequestHandler, HTTPServer
from urllib.parse import urlparse
import sqlite3
import json
import sys
import re
import os
import os.path

import repository
import util
import config

class HTTPHandler(BaseHTTPRequestHandler):

    def __init__(self, request, client_address, server):
        self._repository = repository.Repository(config.database)
        self._handlers = {}
        self._post_data = None
        self.register_handler('/interface_main', self.get_interface_main, method='GET')
        self.register_handler('/interface_main', self.post_interface_main, method='POST')
        self.register_handler('/interface_type', self.get_interface_type, method='GET')
        self.register_handler('/interface_table', self.get_interface_table, method='GET')
        BaseHTTPRequestHandler.__init__(self, request, client_address, server)

    def get_interface_main(self):
        self.send_json(util.to_dict_list(self._repository.list_interface_main()))

    def post_interface_main(self):
        input = self._post_data
        interface_type = self._repository.get_interface_type(input['type'])
        interface_id = self._repository.insert_interface_main(interface_type.id)
        self._repository.insert_interface_table(interface_type.table_name, input['fields'])

        # {'type': '1', 'fields': {'label': 'a', 'description': 'bc', 'function_name': 'c'}, 'parameters': [{'name': 'a', 'type': '', 'desc': '', 'optional': '', 'required': ''}]}
        # TODO 插入参数
        
        self.send_json({})

    def get_interface_type_id(self, name):
        return {
            'LBM': 1,
            'URL': 2,
            'TC': 3
        }[name]
    
    def get_interface_type(self):
        self.send_json([x.__dict__ for x in self._repository.list_interface_type()])
    
    def get_interface_table(self):
        self.send_json(self._repository.list_interface_table())
    
    def register_handler(self, url_pattern, handler_function, method="POST"):
        '''handler_function(HTTPServer) -> void'''
        method = method.lower()
        if not method in self._handlers:
            self._handlers[method] = {}
            
        self._handlers[method][url_pattern] = handler_function
    
    def do_GET(self):
        path = self.path
        processed = False
        
        if path == "/":
            self.send_file('index.html')
            processed = True
        elif path.find('.') != -1:
            self.send_file(path)
            processed = True
        elif "get" in self._handlers:
            for url_pattern, handler in self._handlers["get"].items():
                if re.search(url_pattern, path):
                    handler()
                    processed = True

        if not processed:
            self.send_blank()

    def do_POST(self):
        buffer = self.rfile.read(int(self.headers['content-length']))
        # buffer = self.rfile.read() will forever loop
        self._post_data = json.loads(str(buffer, encoding="utf-8"))
        
        path = self.path
        processed = False
        
        if "post" in self._handlers:
            for url_pattern, handler in self._handlers["post"].items():
                if re.search(url_pattern, path):
                    handler()
                    processed = True

        if not processed:
            self.send_blank_json()
        
    def header(self, mime):
        self.send_response(200)
        self.send_header('Content-Type', mime)
        self.end_headers()
            
    def header_json(self):
        self.header("application/json")

    def header_html(self):
        self.header("text/html")

    def header_css(self):
        self.header("text/css")

    def send_blank(self):
        self.header_html()
        self.wfile.write(b'')
        
    def send_blank_json(self):
        self.header_json()
        self.wfile.write(b'{}')
                         
    def send_json(self, data):
        self.header_json()
        self.wfile.write(bytes(json.dumps(data), 'utf-8'))

    def send_file(self, file_name):
        file_name = 'static/' + file_name
        if not os.path.isfile(file_name):
            self.send_blank()
            return
            
        self.header(util.get_mime(file_name))
        with open(file_name, 'rb') as file:
            self.wfile.write(file.read())

