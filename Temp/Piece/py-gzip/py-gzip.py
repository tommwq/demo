
import gzip
import zlib

filename = 'a.gzip'
filename = 'index'
filename = 'c.txt'n

file = open(filename, 'rb')
content = file.read()
file.close()

print zlib.decompress(content)
