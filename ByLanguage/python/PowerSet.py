
# 计算集合的幂集合

import sys

s = [1, 2, 3]

def getSuperSet(s, k):
    if k == len(s):
        sys.stdout.write('{')
        sep = ''
        for x in s:
            if x != -1:
                sys.stdout.write(sep)
                sys.stdout.write(str(x))
                sep = ','
        sys.stdout.write('}\n')
    else:
        getSuperSet(s, k + 1)
        t = s[k]
        s[k] = -1
        getSuperSet(s, k + 1)
        s[k] = t
        
        
print getSuperSet(s, 0)

a = raw_input('pause')
