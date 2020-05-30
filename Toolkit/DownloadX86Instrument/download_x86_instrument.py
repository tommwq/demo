'''
从https://www.felixcloutier.com/x86/下载x86指令数据。


1. 从https://www.felixcloutier.com/x86/ 加载指令列表。
2. 从https://www.felixcloutier.com/x86/<指令> 加载详细数据。
'''
import urllib.request
from html.parser import HTMLParser
import json

class Parser(HTMLParser):
    def __init__(self):
        super(Parser, self).__init__()

url = "https://www.felixcloutier.com/x86/"
response = urllib.request.urlopen(url)
content = response.read().decode()
parser.feed(content)

