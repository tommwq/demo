

class A:

    def foo(f):
        def wrap(*args, **kwargs):
            x = f(*args, **kwargs)
            print(*args)
            print(**kwargs)
            print(args[0])
            return "decorated " + x
        return wrap

    @foo
    def bar(self):
        return "bar"

print(A().bar())



import random
import string

def random_string(length):
    result = ''
    for i in range(length):
        ch = str(random.choice(string.ascii_lowercase))
        result += ch

    return result

print(random_string(10))
