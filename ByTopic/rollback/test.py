# class A:

#     def foo(f):
#         def wrap(*args, **kwargs):
#             x = f(*args, **kwargs)
#             print(*args)
#             print(**kwargs)
#             print(args[0])
#             return "decorated " + x
#         return wrap

#     @foo
#     def bar(self):
#         return "bar"

# print(A().bar())

# import random
# import string

# def random_string(length):
#     result = ''
#     for i in range(length):
#         ch = str(random.choice(string.ascii_lowercase))
#         result += ch

#     return result

# print(random_string(10))



class Entity(object):
    def __init__(self, **kwargs):
        self.__dict__ = kwargs
        
    def __str__(self):
        return str(self.__dict__)


a = [Entity(a=1,b=2)]
print(a[0])
for x in a:
    if x.a == 1:
        x.b = 3

print(a[0])
