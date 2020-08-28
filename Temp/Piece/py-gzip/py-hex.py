
string = 'abcdefadfasdfafds1234123d'

remain = len(string)
line_width = 8
while 0 < remain:
    num = min(line_width, remain)
    for i in range(0, num):
        c = string[len(string) - remain + i]
        print '%2x' % ord(c),
    for i in range(num, line_width):
        print '  ',
    print '  ',
    for i in range(0, num):
        c = string[len(string) - remain + i]
        print '%c' % c,
    print ' '
    remain = remain - num
    
