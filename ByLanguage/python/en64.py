#! /usr/bin/env python
# encoding: utf8

'''
将文件采用base64编码。可以用来生成data URL scheme或者通过ASCII码传输二进制数据。
支持Python 2.x和3.x

see also:
  http://en.wikipedia.org/wiki/Data_URI_scheme
  http://en.wikipedia.org/wiki/URI_scheme
  http://tools.ietf.org/html/rfc2397
'''

import base64
import sys

usage = '''
usage: 
  $ python en64.py binary_file [options]
  options:
      -o output_file
'''

def main():
	argv_len = len(sys.argv)
	
	if argv_len == 1:
		print(usage)
		return
	
	input_filename = sys.argv[1]
	output_filename = None
	
	for i in range(1, argv_len):
		if sys.argv[i] == '-o' and i + 1 < argv_len:
			output_filename = sys.argv[i + 1]
			break
	
	input_file = open(input_filename, 'rb')
	
	count = 0
	data = b'';
	
	for line in input_file:
		data = data + line
		
	string = base64.b64encode(data).decode('ascii')
	
	output_file = None
	if output_filename:
		output_file = open(output_filename, 'w')
	
	if output_file:
		output_file.write(string)
	else:
		print(string)
		
	return
	
if __name__ == '__main__':
	main()
