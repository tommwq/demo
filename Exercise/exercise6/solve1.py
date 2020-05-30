
class Solve(object):
    def __init__(self):
        self._character = [0x38, 0x6c, 0x44, 0x40,
                           0x40, 0x40, 0x44, 0x4c,
                           0x38]
        
    def solve(self):
        self.print_character()

    def print_character(self):
        for i in range(0, len(self._character)):
            for j in range(0, 8):
                mask = 1 << (7 - j)
                self.print_dot(self._character[i] & mask != 0)
            print()

    def print_dot(self, solid):
        ch = ' '
        if solid:
            ch = '*'
            
        print(ch, end='')
            

        

Solve().solve()
