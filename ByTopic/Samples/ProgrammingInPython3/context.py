class Foo:
    __slots__ = ('count')
    
    def __init__(self):
        self.count = 0

    def __enter__(self, *args):
        print('enter', *args, self.count)
        return self

    def __exit__(self, *args):
        self.count += 1
        print('exit', *args, self.count)


with Foo() as foo:
    pass
