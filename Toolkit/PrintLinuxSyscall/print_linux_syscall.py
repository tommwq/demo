'''
遍历Linux代码目录，读取*.tbl文件，打印系统调用。
'''

import os
import re

class LinuxSyscall(object):
    def __init__(self):
        self.number = -1
        self.name = ''
        self.signature =''


    def __str__(self):
        return '<LinuxSyscall number={},name={},signature={}>'.format(
            self.number,
            self.name,
            self.signature
        )

class LinuxSyscallParser(object):
    
    def __init__(self, linux_kernel_source_directory):
        self.root_directory = linux_kernel_source_directory
        self.syscalls = []

    def parse(self, bit=64):
        entry_file_name = os.path.join(
            self.root_directory,
            "arch/x86/entry/syscalls/syscall_{}.tbl".format(bit)
        )

        syscall_table = {}
        for syscall in self.parse_syscall(entry_file_name):
            syscall_table[syscall.name] = syscall
            
        header_file_name = os.path.join(
            root_directory,
            "include/linux/syscalls.h"
        )
        with open(header_file_name, 'r') as input_file:
            lines = [line.strip() for line in input_file.readlines() if not line.startswith('#')]
            for line in ''.join(lines).replace('}', '};').replace('*/', '*/;').split(';'):
                if not line.startswith('asmlinkage'):
                    continue
                line = line.replace('\t', ' ')
                line = re.sub(' +', ' ', line)
                line = line[11:] + ';'
                signature = line
                name = self.get_syscall_name_from_signature(signature.replace('sys_', ''))
                if name in syscall_table:
                    syscall_table[name].signature = signature
                 
        result = [x for x in syscall_table.values()]
        result.sort(key=lambda s: s.number)
        return result


    def get_syscall_name_from_signature(self, signature):
        pos1 = signature.find(' ')
        pos2 = signature.find('(')
        return signature[pos1 + 1:pos2]

    def parse_syscall(self, entry_file_name):
        result = []
        with open(entry_file_name, 'r') as input_file:
            for line in input_file.readlines():
                line = line.strip()
                if len(line) == 0:
                    continue
                if line.startswith('#'):
                    continue
                line = re.sub(' +', '\t', line)
                blocks = [block for block in line.split('\t') if len(block) > 0]
                try:
                    syscall = LinuxSyscall()
                    syscall.number = int(blocks[0])
                    syscall.name = blocks[2]
                    result.append(syscall)
                except:
                    raise
                    
        return result

    
if __name__ == '__main__':
    root_directory = 'd:/SourceCode/linux/'
    parser = LinuxSyscallParser(root_directory)
    bit = 32
    syscalls = parser.parse(bit)
    sql_format = "INSERT INTO linuxsyscall" + str(bit) + " (Number,Name,Signature) VALUES ({},'{}','{}');"
    for syscall in syscalls:
        print(sql_format.format(syscall.number, syscall.name, syscall.signature))

