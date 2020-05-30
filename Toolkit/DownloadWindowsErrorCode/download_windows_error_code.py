'''
从微软网站下载Windows错误码并打印。

2019年11月15日
'''

import urllib
import urllib.request
from html.parser import HTMLParser
import json

class ErrorDescription(object):
    def __init__(self):
        self.reset()

    def reset(self):
        self.name = ''
        self.code = ''
        self.description = ''

    def __str__(self):
        return '<ErrorDescription name={},code={},description={}>'.format(self.name, self.code, self.description)

    def to_dict(self):
        return {
            "name": self.name,
            "code": self.code,
            "description": self.description
        }

class Parser(HTMLParser):
    def __init__(self):
        super(Parser, self).__init__()
        self.dt = False
        self.dd = False
        self.p = False
        self.strong = False
        self.desc = ErrorDescription()
        self.desc_list = []
        
    def handle_starttag(self, tag, attrs):
        if tag == 'dd':
            self.dd = True
        elif tag == 'dt':
            self.dt = True
        elif tag == 'p':
            self.p = True
        elif tag == 'strong':
            self.strong = True

    def handle_endtag(self, tag):
        if tag == 'dd':
            self.dd = False
        elif tag == 'dt':
            self.dt = False
        elif tag == 'p':
            self.p = False
        elif tag == 'strong':
            self.strong = False

    def handle_data(self, data):
        if self.dt and self.strong:
            self.desc.name = data
        elif self.dd and self.dt and self.p:
            pos = data.find(' ')
            if self.desc.code == '':
                try:
                    num = int(data[:pos])
                    self.desc.code = data[:pos]
                except:
                    pass
            else:
                self.desc.description = data.replace("\\", "\\\\").replace("\"", "\\\"")
                self.desc_list.append(self.desc)
                self.desc = ErrorDescription()


url_list = [
    "https://docs.microsoft.com/en-us/windows/win32/debug/system-error-codes--0-499-",
    "https://docs.microsoft.com/en-us/windows/win32/debug/system-error-codes--500-999-",
    "https://docs.microsoft.com/en-us/windows/win32/debug/system-error-codes--1000-1299-",
    "https://docs.microsoft.com/en-us/windows/win32/debug/system-error-codes--1300-1699-",
    "https://docs.microsoft.com/en-us/windows/win32/debug/system-error-codes--1700-3999-",
    "https://docs.microsoft.com/en-us/windows/win32/debug/system-error-codes--4000-5999-",
    "https://docs.microsoft.com/en-us/windows/win32/debug/system-error-codes--6000-8199-",
    "https://docs.microsoft.com/en-us/windows/win32/debug/system-error-codes--8200-8999-",
    "https://docs.microsoft.com/en-us/windows/win32/debug/system-error-codes--9000-11999-",
    "https://docs.microsoft.com/en-us/windows/win32/debug/system-error-codes--12000-15999-"
]

parser = Parser()
for url in url_list:
    response = urllib.request.urlopen(url)
    content = response.read().decode()
    parser.feed(content)


print(json.dumps([desc.to_dict() for desc in parser.desc_list]))
