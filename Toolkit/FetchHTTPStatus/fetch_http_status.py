'''
从百度百科下载HTTP状态码。

https://baike.baidu.com/item/HTTP%E7%8A%B6%E6%80%81%E7%A0%81/5053660
'''

import http.client
import urllib.parse
import html.parser

class Stack(object):
    def __init__(self):
        self._stack = []

    def empty(self):
        return self.size() == 0
        
    def size(self):
        return len(self._stack)
    
    def top(self):
        return self._stack[self.size() - 1]

    def pop(self):
        self._stack = self._stack[:-1]

    def push(self, x):
        self._stack.append(x)
        

class MyHtmlParser(html.parser.HTMLParser):
    def __init__(self):
        super(MyHtmlParser, self).__init__()
        self._in_div = False
        self._stack = Stack()
    
    def handle_starttag(self, tag, attrs):
        if self._in_div:
            self._stack.push(tag)
        elif self.is_target_div(tag, attrs):
            self._in_div = True

    def handle_endtag(self, tag):
        if self._in_div:
            self._stack.pop()

    def handle_data(self, data):
        if not self._in_div:
            return
        print(data.strip())

    def is_target_div(self, tag, attrs):
        if tag != 'div':
            return False

        return self.has_attr_value(attrs, "class", "para-title level-3")

    def has_attr_value(self, attrs, name, value):
        return len([attr for attr in attrs if attr[0] == name and attr[1] == value]) > 0
        

host = "baike.baidu.com"
path = "/item/HTTP状态码/5053660"
encoded = urllib.parse.quote(path)

connection = http.client.HTTPSConnection(host)
connection.request("GET", encoded)
response = connection.getresponse()
content = response.read().decode("utf-8")
parser = MyHtmlParser()
parser.feed(content)

